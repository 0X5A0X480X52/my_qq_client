package cn.amatrix.utils.base64;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

/**
 * Base64Transformer 类提供了将图像转换为 Base64 字符串和将 Base64 字符串转换为圆形图像的方法。
 */
public class ImageManager {
    /**
     * 将图片压缩为 40px*40px 并转换为 Base64 字符串。
     *
     * @param imagePath 图片的文件路径
     * @return 图片的 Base64 字符串
     * @throws IOException 如果读取或写入图片时发生错误
     */
    public static String imageToBase64(String imagePath, int width, int height) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(imagePath));
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage bufferedScaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedScaledImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedScaledImage, "jpg", baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    /**
     * 将 Base64 字符串转换为圆形图像。
     *
     * @param base64Image Base64 字符串
     * @return 圆形的 BufferedImage 对象
     * @throws IOException 如果读取图片时发生错误
     */
    public static BufferedImage base64ToCircularImage(String base64Image) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
        if (image != null) {
            return cropToCircle(image);
        }
        return null;
    }

    /**
     * 将图像裁剪为圆形。
     *
     * @param image 要裁剪的图像
     * @return 圆形的 BufferedImage 对象
     */
    public static BufferedImage cropToCircle(BufferedImage image) {
        int diameter = Math.min(image.getWidth(), image.getHeight());
        BufferedImage mask = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = mask.createGraphics();
        applyQualityRenderingHints(g2d);
        g2d.fillOval(0, 0, diameter, diameter);
        g2d.dispose();

        BufferedImage cropped = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        g2d = cropped.createGraphics();
        applyQualityRenderingHints(g2d);
        int x = (diameter - image.getWidth()) / 2;
        int y = (diameter - image.getHeight()) / 2;
        g2d.drawImage(image, x, y, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
        g2d.drawImage(mask, 0, 0, null);
        g2d.dispose();

        return cropped;
    }

    /**
     * 应用高质量的渲染提示。
     *
     * @param g2d Graphics2D 对象
     */
    private static void applyQualityRenderingHints(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    }

    public static BufferedImage resizeImage(BufferedImage image, int width, int height) throws IOException {
        return Thumbnails.of(image)
                         .size(width, height)
                         .asBufferedImage();
    }

    /**
     * 主方法，用于测试图像转换功能。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        try {
            String base64String = imageToBase64("C:\\UserFiles\\图片\\testPictures\\v2-22e10b51194d2703d76454523ef2f42b_r.jpg", 256, 256);
            // String base64String = imageToBase64("C:\\UserFiles\\图片\\testPictures\\tomolin.png", 256, 256);
            System.out.println(base64String);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
