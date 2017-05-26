package utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 *  Created by zhanjiahan on 17-5-26.
 */
public class StdDraw implements ActionListener, MouseListener, MouseMotionListener, KeyListener {

    public static final Color BLACK = Color.BLACK;
    public static final Color BLUE = Color.BLUE;
    public static final Color CYAN = Color.CYAN;
    public static final Color DARK_GRAY = Color.DARK_GRAY;
    public static final Color GRAY = Color.GRAY;
    public static final Color GREEN = Color.GREEN;
    public static final Color LIGHT_GRAY = Color.LIGHT_GRAY;
    public static final Color MAGENTA = Color.MAGENTA;
    public static final Color ORANGE = Color.ORANGE;
    public static final Color PINK = Color.PINK;
    public static final Color RED = Color.RED;
    public static final Color WHITE = Color.WHITE;
    public static final Color YELLOW = Color.YELLOW;

    public static final Color BOOK_BLUE = new Color(9, 90, 160);
    public static final Color LIGHT_BOOK_BLUE = new Color(103, 198, 243);
    public static final Color BOOK_RED = new Color(150, 35, 31);
    public static final Color PRINCETON_ORANGE = new Color(245, 128, 37);

    // 默认颜色
    private static final Color DEFAULT_PEN_COLOR = BLACK;
    private static final Color DEFAULT_CLEAR_COLOR = WHITE;

    // 当前画笔颜色
    private static Color penColor;

    // 画布默认大小
    private static final int DEFAULT_SIZE = 512;
    private static int width = DEFAULT_SIZE;
    private static int height = DEFAULT_SIZE;

    // 默认画笔粗细(radius)
    private static final double DEFAULT_PEN_RADIUS = 0.002;

    // 当前画笔粗细
    private static double penRadius;

    // 是立即开始画,还是等下一次显示时绘制
    private static boolean defer = false;

    // 画布的边界
    private static final double BORDER = 0.00;
    private static final double DEFAULT_XMIN = 0.00;
    private static final double DEFAULT_XMAX = 1.00;
    private static final double DEFAULT_YMIN = 0.00;
    private static final double DEFAULT_YMAX = 1.00;
    private static double xmin, xmax, ymin, ymax;

    // for synchronization同步
    private static Object mouseLock = new Object();
    private static Object keyLock = new Object();

    // 默认字体
    private static final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 16);

    // 当前字体
    private static Font font;

    // double buffered graphics
    private static BufferedImage offscreenImage, onscreenImage;
    private static Graphics2D offscreen, onscreen;

    // 回调单例,避免生成多个.class
    private static StdDraw std = new StdDraw();

    // 屏幕绘制显示Frame
    private static JFrame frame;

    // 鼠标状态
    private static boolean mousePressed = false;
    private static double mouseX = 0;
    private static double mouseY = 0;

    // 键盘点击事件队列(queue of typed key characters)
    private static LinkedList<Character> keysTyped = new LinkedList<Character>();

    // 当前键盘快捷键事件(set of key codes currently pressed down)
    private static TreeSet<Integer> keysDown = new TreeSet<Integer>();

    // 单例模式, 不能被实例化
    private StdDraw() {

    }

    // static initializer
    static {
        init();
    }

    public static void setCanvasSize() {
        setCanvasSize(DEFAULT_SIZE, DEFAULT_SIZE);
    }

    public static void setCanvasSize(int canvasWidth, int canvasHeight) {
        if (canvasWidth <=0 || canvasHeight <= 0) {
            throw new IllegalArgumentException("宽高都必须大于0");
        }
        width = canvasWidth;
        height = canvasHeight;
        init();
    }

    // init
    private static void init() {
        if (frame != null) {
            frame.setVisible(false);
        }
        frame = new JFrame();
        offscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        onscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        offscreen = offscreenImage.createGraphics();
        onscreen = onscreenImage.createGraphics();
        setXscale();
        setYscale();
        offscreen.setColor(DEFAULT_CLEAR_COLOR);
        offscreen.fillRect(0, 0, width, height);
        setPenColor();
        setPenRadius();
        setFont();
        clear();

        // add antialiasing
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        offscreen.addRenderingHints(hints);

        // frame stuff
        ImageIcon icon = new ImageIcon(onscreenImage);
        JLabel draw = new JLabel(icon);

        draw.addMouseListener(std);
        draw.addMouseMotionListener(std);

        frame.setContentPane(draw);
        frame.addKeyListener(std);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close all windows
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close only current window
        frame.setTitle("Standard Dtaw");
        frame.setJMenuBar(createMenuBar());
        frame.pack();
        frame.requestFocusInWindow();
        frame.setVisible(true);
    }

    // create menu bar
    private static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        JMenuItem menuItem1 = new JMenuItem(" Save ...");
        menuItem1.addActionListener(std);
        menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menu.add(menuItem1);

        return menuBar;
    }

    public static void setXscale() {
        setXscale(DEFAULT_XMIN, DEFAULT_XMAX);
    }

    public static void setYscale() {
        setYscale(DEFAULT_YMIN, DEFAULT_YMAX);
    }

    public static void setScale() {
        setXscale();
        setYscale();
    }

    public static void setXscale(double min, double max) {
        double size = max - min;
        if (size == 0.0) {
            throw new IllegalArgumentException("x: min和max一样大");
        }
        synchronized (mouseLock) {
            xmin = min - BORDER * size;
            xmax = max + BORDER * size;
        }
    }

    public static void setYscale(double min, double max) {
        double size = max - min;
        if (size == 0.0) {
            throw new IllegalArgumentException("y: min和max一样大");
        }
        synchronized (mouseLock) {
            ymin = min - BORDER * size;
            ymax = max + BORDER * size;
        }
    }

    public static void setScale(double min, double max) {
        double size = max - min;
        if (size == 0.0) {
            throw new IllegalArgumentException("min和max一样大");
        }
        synchronized (mouseLock) {
            xmin = min - BORDER * size;
            xmax = max + BORDER * size;
            ymin = min - BORDER * size;
            ymax = max + BORDER * size;
        }
    }

    private static double scaleX(double x) {
        return width * (x - xmin) / (xmax - xmin);
    }
    private static double scaleY(double y) {
        return height * (y - ymin) / (ymax - ymin);
    }

    private static double factorX(double w) {
        return w * width / Math.abs(xmax - xmin);
    }
    private static double factorY(double h) {
        return h * height / Math.abs(ymax - ymin);
    }

    private static double userX(double x) {
        return xmin + x * (xmax - xmin) / width;
    }
    private static double usetY(double y) {
        return ymin - y * (ymax - ymin) / height;
    }


    public static Color getPenColor() {
        return penColor;
    }

    public static void setPenColor() {
        setPenColor(DEFAULT_PEN_COLOR);
    }
    public static void setPenColor(Color color) {
        if (color == null) {
            throw new IllegalArgumentException("颜色不能为空");
        }
        penColor = color;
        offscreen.setColor(penColor);
    }
    public static void setPenColor(int red, int green, int blue) {
        if (red < 0 || red > 255) {
            throw new IllegalArgumentException("amount of red must be between 0 and 255");
        }
        if (green < 0 || green > 255) {
            throw new IllegalArgumentException("amount of green must be between 0 and 255");
        }
        if (blue < 0 || blue > 255) {
            throw new IllegalArgumentException("amount of blue must be between 0 and 255");
        }
        setPenColor(new Color(red, green, blue));
    }

    public static double getPenRadius() {
        return penRadius;
    }

    public static void setPenRadius() {
        setPenRadius(DEFAULT_PEN_RADIUS);
    }
    public static void setPenRadius(double radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("笔的粗细为非负");
        }
        penRadius = radius;
        float scaledPenRadius = (float) (radius * DEFAULT_SIZE);
        BasicStroke stroke = new BasicStroke(scaledPenRadius, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        offscreen.setStroke(stroke);
    }

    public static void clear() {
        clear(DEFAULT_CLEAR_COLOR);
    }
    public static void clear(Color color) {
        offscreen.setColor(color);
        offscreen.fillRect(0, 0, width, height);
        offscreen.setColor(penColor);
        draw();
    }

    public static Font getFont() {
        return font;
    }

    public static void setFont() {
        setFont(DEFAULT_FONT);
    }
    public static void setFont(Font font) {
        if (font == null) {
            throw new IllegalArgumentException("字体不能为空");
        }
        StdDraw.font = font;
    }

    /**
     * Drawing geometric shapes
     */

    public static void line(double x0, double y0, double x1, double y1) {
        offscreen.draw(new Line2D.Double(scaleX(x0), scaleY(y0), scaleX(x1), scaleY(y1)));
        draw();
    }

    private static void pixel(double x, double y) {
        offscreen.fillRect((int) Math.round(scaleX(x)), (int) Math.round(scaleY(y)), 1, 1);
    }

    public static void point(double x, double y) {
        double xs = scaleX(x);
        double ys = scaleY(y);
        double r = penRadius;
        float scaledPenRadius = (float) (r * DEFAULT_SIZE);

        if (scaledPenRadius <= 1) {
            pixel(x, y);
        } else {
            offscreen.fill(new Ellipse2D.Double(xs - scaledPenRadius / 2, ys - scaledPenRadius / 2, scaledPenRadius, scaledPenRadius));
        }

        draw();
    }

    public static void circle(double x, double y, double radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("radius非负");
        }
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * radius);
        double hs = factorY(2 * radius);
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            offscreen.draw(new Ellipse2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        }
        draw();
    }

    public static void filledCircle(double x, double y, double radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("radius非负");
        }
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * radius);
        double hs = factorY(2 * radius);
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            offscreen.fill(new Ellipse2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        }
        draw();
    }

    public static void ellipse(double x, double y, double semiMajorAxis, double semiMinorAxis) {
        if (!(semiMajorAxis >= 0)) {
            throw new IllegalArgumentException("semiMajorAxis must be nonnegative");
        }
        if (!(semiMinorAxis >= 0)) {
            throw new IllegalArgumentException("semiMinorAxis must be nonnegative");
        }
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * semiMajorAxis);
        double hs = factorY(2 * semiMinorAxis);
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            offscreen.draw(new Ellipse2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        }
        draw();
    }

    public static void filledEllipse(double x, double y, double semiMajorAxis, double semiMinorAxis) {
        if (!(semiMajorAxis >= 0)) {
            throw new IllegalArgumentException("semiMajorAxis must be nonnegative");
        }
        if (!(semiMinorAxis >= 0)) {
            throw new IllegalArgumentException("semiMinorAxis must be nonnegative");
        }
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * semiMajorAxis);
        double hs = factorY(2 * semiMinorAxis);
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            offscreen.fill(new Ellipse2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        }
        draw();
    }

    public static void arc(double x, double y, double radius, double angle1, double angle2) {
        if (radius < 0) throw new IllegalArgumentException("arc radius must be nonnegative");
        while (angle2 < angle1) angle2 += 360;
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*radius);
        double hs = factorY(2*radius);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.draw(new Arc2D.Double(xs - ws/2, ys - hs/2, ws, hs, angle1, angle2 - angle1, Arc2D.OPEN));
        draw();
    }

    public static void square(double x, double y, double halfLength) {
        if (!(halfLength >= 0)) throw new IllegalArgumentException("half length must be nonnegative");
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*halfLength);
        double hs = factorY(2*halfLength);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.draw(new Rectangle2D.Double(xs - ws/2, ys - hs/2, ws, hs));
        draw();
    }

    public static void filledSquare(double x, double y, double halfLength) {
        if (!(halfLength >= 0)) throw new IllegalArgumentException("half length must be nonnegative");
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*halfLength);
        double hs = factorY(2*halfLength);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.fill(new Rectangle2D.Double(xs - ws/2, ys - hs/2, ws, hs));
        draw();
    }

    public static void rectangle(double x, double y, double halfWidth, double halfHeight) {
        if (!(halfWidth  >= 0)) throw new IllegalArgumentException("half width must be nonnegative");
        if (!(halfHeight >= 0)) throw new IllegalArgumentException("half height must be nonnegative");
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*halfWidth);
        double hs = factorY(2*halfHeight);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.draw(new Rectangle2D.Double(xs - ws/2, ys - hs/2, ws, hs));
        draw();
    }

    public static void filledRectangle(double x, double y, double halfWidth, double halfHeight) {
        if (!(halfWidth  >= 0)) throw new IllegalArgumentException("half width must be nonnegative");
        if (!(halfHeight >= 0)) throw new IllegalArgumentException("half height must be nonnegative");
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*halfWidth);
        double hs = factorY(2*halfHeight);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.fill(new Rectangle2D.Double(xs - ws/2, ys - hs/2, ws, hs));
        draw();
    }


    public static void polygon(double[] x, double[] y) {
        if (x == null) throw new IllegalArgumentException("x-coordinate array is null");
        if (y == null) throw new IllegalArgumentException("y-coordinate array is null");
        int n1 = x.length;
        int n2 = y.length;
        if (n1 != n2) throw new IllegalArgumentException("arrays must be of the same length");
        int n = n1;
        if (n == 0) return;

        GeneralPath path = new GeneralPath();
        path.moveTo((float) scaleX(x[0]), (float) scaleY(y[0]));
        for (int i = 0; i < n; i++)
            path.lineTo((float) scaleX(x[i]), (float) scaleY(y[i]));
        path.closePath();
        offscreen.draw(path);
        draw();
    }

    public static void filledPolygon(double[] x, double[] y) {
        if (x == null) throw new IllegalArgumentException("x-coordinate array is null");
        if (y == null) throw new IllegalArgumentException("y-coordinate array is null");
        int n1 = x.length;
        int n2 = y.length;
        if (n1 != n2) throw new IllegalArgumentException("arrays must be of the same length");
        int n = n1;
        if (n == 0) return;

        GeneralPath path = new GeneralPath();
        path.moveTo((float) scaleX(x[0]), (float) scaleY(y[0]));
        for (int i = 0; i < n; i++)
            path.lineTo((float) scaleX(x[i]), (float) scaleY(y[i]));
        path.closePath();
        offscreen.fill(path);
        draw();
    }

    /**
     * Drawing Images.
     */

    // get image from the given filename
    private static Image getImage(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("图片文件名不能为空");
        }

        // to read from file
        ImageIcon icon = new ImageIcon(filename);

        // try to read from file
        if (icon == null || icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            try {
                URL url = new URL(filename);
                icon = new ImageIcon(url);
            } catch (MalformedURLException e) {
                /* not a url */
                StdOut.println("not a url");
            }
        }

        // in case file is inside a .jar (classpath relative to StdDraw)
        if (icon == null || icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            URL url = StdDraw.class.getResource(filename);
            if (url != null) {
                icon = new ImageIcon(url);
            }
        }

        // in case file is inside a .jar (classpath relative to root of jar)
        if (icon == null || icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            URL url = StdDraw.class.getResource("/" + filename);
            if (url == null) {
                throw new IllegalArgumentException("image: " + filename + " not found");
            }
            icon = new ImageIcon(url);
        }

        return icon.getImage();
    }

    public static void picture(double x, double y, String filename) {
        // BufferedImage image = getImage(filename);
        Image image = getImage(filename);
        double xs = scaleX(x);
        double ys = scaleY(y);
        // int ws = image.getWidth();    // can call only if image is a BufferedImage
        // int hs = image.getHeight();
        int ws = image.getWidth(null);
        int hs = image.getHeight(null);
        if (ws < 0 || hs < 0) throw new IllegalArgumentException("image " + filename + " is corrupt");

        offscreen.drawImage(image, (int) Math.round(xs - ws/2.0), (int) Math.round(ys - hs/2.0), null);
        draw();
    }

    public static void picture(double x, double y, String filename, double degrees) {
        // BufferedImage image = getImage(filename);
        Image image = getImage(filename);
        double xs = scaleX(x);
        double ys = scaleY(y);
        // int ws = image.getWidth();    // can call only if image is a BufferedImage
        // int hs = image.getHeight();
        int ws = image.getWidth(null);
        int hs = image.getHeight(null);
        if (ws < 0 || hs < 0) throw new IllegalArgumentException("image " + filename + " is corrupt");

        offscreen.rotate(Math.toRadians(-degrees), xs, ys);
        offscreen.drawImage(image, (int) Math.round(xs - ws/2.0), (int) Math.round(ys - hs/2.0), null);
        offscreen.rotate(Math.toRadians(+degrees), xs, ys);

        draw();
    }

    public static void picture(double x, double y, String filename, double scaledWidth, double scaledHeight) {
        Image image = getImage(filename);
        if (scaledWidth  < 0) throw new IllegalArgumentException("width  is negative: " + scaledWidth);
        if (scaledHeight < 0) throw new IllegalArgumentException("height is negative: " + scaledHeight);
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(scaledWidth);
        double hs = factorY(scaledHeight);
        if (ws < 0 || hs < 0) throw new IllegalArgumentException("image " + filename + " is corrupt");
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else {
            offscreen.drawImage(image, (int) Math.round(xs - ws/2.0),
                    (int) Math.round(ys - hs/2.0),
                    (int) Math.round(ws),
                    (int) Math.round(hs), null);
        }
        draw();
    }


    public static void picture(double x, double y, String filename, double scaledWidth, double scaledHeight, double degrees) {
        if (scaledWidth < 0) throw new IllegalArgumentException("width is negative: " + scaledWidth);
        if (scaledHeight < 0) throw new IllegalArgumentException("height is negative: " + scaledHeight);
        Image image = getImage(filename);
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(scaledWidth);
        double hs = factorY(scaledHeight);
        if (ws < 0 || hs < 0) throw new IllegalArgumentException("image " + filename + " is corrupt");
        if (ws <= 1 && hs <= 1) pixel(x, y);

        offscreen.rotate(Math.toRadians(-degrees), xs, ys);
        offscreen.drawImage(image, (int) Math.round(xs - ws/2.0),
                (int) Math.round(ys - hs/2.0),
                (int) Math.round(ws),
                (int) Math.round(hs), null);
        offscreen.rotate(Math.toRadians(+degrees), xs, ys);

        draw();
    }

    /**
     * Drawing text.
     */

    public static void text(double x, double y, String text) {
        if (text == null) throw new IllegalArgumentException();
        offscreen.setFont(font);
        FontMetrics metrics = offscreen.getFontMetrics();
        double xs = scaleX(x);
        double ys = scaleY(y);
        int ws = metrics.stringWidth(text);
        int hs = metrics.getDescent();
        offscreen.drawString(text, (float) (xs - ws/2.0), (float) (ys + hs));
        draw();
    }

    public static void text(double x, double y, String text, double degrees) {
        if (text == null) throw new IllegalArgumentException();
        double xs = scaleX(x);
        double ys = scaleY(y);
        offscreen.rotate(Math.toRadians(-degrees), xs, ys);
        text(x, y, text);
        offscreen.rotate(Math.toRadians(+degrees), xs, ys);
    }

    public static void textLeft(double x, double y, String text) {
        if (text == null) throw new IllegalArgumentException();
        offscreen.setFont(font);
        FontMetrics metrics = offscreen.getFontMetrics();
        double xs = scaleX(x);
        double ys = scaleY(y);
        int hs = metrics.getDescent();
        offscreen.drawString(text, (float) xs, (float) (ys + hs));
        draw();
    }

    public static void textRight(double x, double y, String text) {
        if (text == null) throw new IllegalArgumentException();
        offscreen.setFont(font);
        FontMetrics metrics = offscreen.getFontMetrics();
        double xs = scaleX(x);
        double ys = scaleY(y);
        int ws = metrics.stringWidth(text);
        int hs = metrics.getDescent();
        offscreen.drawString(text, (float) (xs - ws), (float) (ys + hs));
        draw();
    }
    public static void pause(int t) {
        try {
            Thread.sleep(t);
        }
        catch (InterruptedException e) {
            System.out.println("Error sleeping");
        }
    }

    public static void show() {
        onscreen.drawImage(offscreenImage, 0, 0, null);
        frame.repaint();
    }

    // draw onscreen if defer is false
    private static void draw() {
        if (!defer) show();
    }

    public static void enableDoubleBuffering() {
        defer = true;
    }

    public static void disableDoubleBuffering() {
        defer = false;
    }

    /**
     * Save drawing to a file
     */
    public static void save(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException();
        }
        File file = new File(filename);
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);

        // png file
        if ("png".equals(suffix)) {
            try {
                ImageIO.write(onscreenImage, suffix, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ("jpg".equals(suffix)) {
            WritableRaster raster = onscreenImage.getRaster();
            WritableRaster newRaster;
            newRaster = raster.createWritableChild(0, 0, width, height, 0, 0, new int[] {0, 1, 2});
            DirectColorModel cm = (DirectColorModel) onscreenImage.getColorModel();
            DirectColorModel newCM = new DirectColorModel(cm.getPixelSize(), cm.getRedMask(), cm.getGreenMask(), cm.getBlueMask());
            BufferedImage rgbBuffer = new BufferedImage(newCM, newRaster, false, null);
            try {
                ImageIO.write(rgbBuffer, suffix, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid image file type: " + suffix);
        }

    }

    public void actionPerformed(ActionEvent e) {
        FileDialog chooser = new FileDialog(StdDraw.frame, "Use a .png or .jpg extension", FileDialog.SAVE);
        chooser.setVisible(true);
        String filename = chooser.getFile();
        if (filename != null) {
            StdDraw.save(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }

    /**
     * Mouse interactions.
     */

    public static boolean mousePressed() {
        synchronized (mouseLock) {
            return mousePressed;
        }
    }

    public static double mouseX() {
        synchronized (mouseLock) {
            return mouseX;
        }
    }

    public static double mouseY() {
        synchronized (mouseLock) {
            return mouseY;
        }
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        synchronized (mouseLock) {
            mouseX = StdDraw.userX(e.getX());
            mouseY = StdDraw.usetY(e.getY());
            mousePressed = true;
        }

    }

    public void mouseReleased(MouseEvent e) {
        synchronized (mouseLock) {
            mousePressed = false;
        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {
        synchronized (mouseLock) {
            mouseX = StdDraw.userX(e.getX());
            mouseY = StdDraw.usetY(e.getY());
        }
    }

    public void mouseMoved(MouseEvent e) {
        synchronized (mouseLock) {
            mouseX = StdDraw.userX(e.getX());
            mouseY = StdDraw.usetY(e.getY());
        }
    }


    /**
     * Keyboard interactions.
     */

    public static boolean hasNextKey() {
        synchronized (keyLock) {
            return !keysTyped.isEmpty();
        }
    }

    public static char nextKeyTyped() {
        synchronized (keyLock) {
            if (keysTyped.isEmpty()) {
                throw new NoSuchElementException();
            }
            return keysTyped.removeLast();
        }
    }

    public static boolean isKeyPressed(int keyboard) {
        synchronized (keyLock) {
            return keysDown.contains(keyboard);
        }
    }

    public void keyTyped(KeyEvent e) {
        synchronized (keyLock) {
            keysTyped.addFirst(e.getKeyChar());
        }
    }

    public void keyPressed(KeyEvent e) {
        synchronized (keyLock) {
            keysDown.add(e.getKeyCode());
        }
    }

    public void keyReleased(KeyEvent e) {
        synchronized (keyLock) {
            keysDown.remove(e.getKeyCode());
        }
    }

    public static void main(String[] args) {
        StdDraw.square(0.2, 0.8, 0.1);
        StdDraw.filledSquare(0.8, 0.8, 0.2);
        StdDraw.circle(0.8, 0.2, 0.2);

        StdDraw.setPenColor(StdDraw.BOOK_RED);
        StdDraw.setPenRadius(0.02);
        StdDraw.arc(0.8, 0.2, 0.1, 200, 45);

        // draw a blue diamond
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
        double[] x = { 0.1, 0.2, 0.3, 0.2 };
        double[] y = { 0.2, 0.3, 0.2, 0.1 };
        StdDraw.filledPolygon(x, y);

        // text
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(0.2, 0.5, "black text");
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(0.8, 0.8, "white text");
    }
}
