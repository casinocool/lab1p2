package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    /**
     * Загружает изображение из файла
     */
    public static BufferedImage loadImage(String filePath) {
        try {
            File file = new File(filePath);
            BufferedImage image = ImageIO.read(file);
            System.out.println("✅ Изображение загружено: " + filePath);
            System.out.println("   Размер: " + image.getWidth() + "x" + image.getHeight());
            System.out.println("   Тип: " + (image.getColorModel().hasAlpha() ? "с альфа-каналом" : "без альфа-канала"));
            return image;
        } catch (IOException e) {
            System.err.println("❌ Ошибка загрузки изображения: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("❌ Неподдерживаемый формат изображения");
            return null;
        }
    }

    /**
     * Извлекает красный канал
     * В результирующем изображении:
     * - Красный канал = исходный красный
     * - Зеленый и синий = 0
     */
    public static BufferedImage extractRedChannel(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage redChannel = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;

                // Оставляем только красный канал, остальные обнуляем
                int redOnly = (red << 16) | (0 << 8) | 0;
                redChannel.setRGB(x, y, redOnly);
            }
        }

        return redChannel;
    }

    /**
     * Извлекает зеленый канал
     * В результирующем изображении:
     * - Зеленый канал = исходный зеленый
     * - Красный и синий = 0
     */
    public static BufferedImage extractGreenChannel(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage greenChannel = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int green = (rgb >> 8) & 0xFF;

                // Оставляем только зеленый канал
                int greenOnly = (0 << 16) | (green << 8) | 0;
                greenChannel.setRGB(x, y, greenOnly);
            }
        }

        return greenChannel;
    }

    /**
     * Извлекает синий канал
     * В результирующем изображении:
     * - Синий канал = исходный синий
     * - Красный и зеленый = 0
     */
    public static BufferedImage extractBlueChannel(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage blueChannel = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int blue = rgb & 0xFF;

                // Оставляем только синий канал
                int blueOnly = (0 << 16) | (0 << 8) | blue;
                blueChannel.setRGB(x, y, blueOnly);
            }
        }

        return blueChannel;
    }

    /**
     * Извлекает красный канал в градациях серого
     */
    public static BufferedImage extractRedAsGrayscale(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage redGrayscale = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;

                // Создаем оттенок серого на основе красного канала
                int gray = (red << 16) | (red << 8) | red;
                redGrayscale.setRGB(x, y, gray);
            }
        }

        return redGrayscale;
    }

    /**
     * Извлекает зеленый канал в градациях серого
     */
    public static BufferedImage extractGreenAsGrayscale(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage greenGrayscale = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int green = (rgb >> 8) & 0xFF;

                // Создаем оттенок серого на основе зеленого канала
                int gray = (green << 16) | (green << 8) | green;
                greenGrayscale.setRGB(x, y, gray);
            }
        }

        return greenGrayscale;
    }

    /**
     * Извлекает синий канал в градациях серого
     */
    public static BufferedImage extractBlueAsGrayscale(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage blueGrayscale = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int blue = rgb & 0xFF;

                // Создаем оттенок серого на основе синего канала
                int gray = (blue << 16) | (blue << 8) | blue;
                blueGrayscale.setRGB(x, y, gray);
            }
        }

        return blueGrayscale;
    }

    /**
     * Создает тестовое изображение с тремя цветными полосами
     */
    public static BufferedImage createTestImage() {
        int width = 300;
        int height = 200;
        BufferedImage testImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Создаем три вертикальные полосы
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb;
                if (x < width / 3) {
                    // Красная полоса
                    rgb = (255 << 16) | (0 << 8) | 0;
                } else if (x < 2 * width / 3) {
                    // Зеленая полоса
                    rgb = (0 << 16) | (255 << 8) | 0;
                } else {
                    // Синяя полоса
                    rgb = (0 << 16) | (0 << 8) | 255;
                }
                testImage.setRGB(x, y, rgb);
            }
        }

        return testImage;
    }

    /**
     * Сохраняет изображение в файл
     */
    public static boolean saveImage(BufferedImage image, String filePath) {
        try {
            // Определяем формат по расширению
            String extension = "png";
            int lastDot = filePath.lastIndexOf('.');
            if (lastDot > 0) {
                extension = filePath.substring(lastDot + 1).toLowerCase();
                if (!extension.equals("png") && !extension.equals("jpg") &&
                        !extension.equals("jpeg") && !extension.equals("bmp")) {
                    extension = "png";
                    filePath = filePath.substring(0, lastDot) + ".png";
                }
            } else {
                filePath = filePath + ".png";
            }

            File outputFile = new File(filePath);
            ImageIO.write(image, extension, outputFile);
            System.out.println("   ✅ Сохранено: " + filePath);
            return true;
        } catch (IOException e) {
            System.err.println("   ❌ Ошибка сохранения: " + e.getMessage());
            return false;
        }
    }

    /**
     * Проверяет существование файла
     */
    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists() && !file.isDirectory();
    }

    /**
     * Анализирует распределение цветов в изображении
     */
    public static void analyzeChannels(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        long totalPixels = width * height;

        long sumRed = 0, sumGreen = 0, sumBlue = 0;
        int maxRed = 0, maxGreen = 0, maxBlue = 0;
        int minRed = 255, minGreen = 255, minBlue = 255;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                sumRed += red;
                sumGreen += green;
                sumBlue += blue;

                maxRed = Math.max(maxRed, red);
                maxGreen = Math.max(maxGreen, green);
                maxBlue = Math.max(maxBlue, blue);

                minRed = Math.min(minRed, red);
                minGreen = Math.min(minGreen, green);
                minBlue = Math.min(minBlue, blue);
            }
        }

        System.out.println("\n📊 Анализ цветовых каналов:");
        System.out.println("   Канал  |  Среднее  |  Мин  |  Макс");
        System.out.println("   -------|-----------|-------|-------");
        System.out.printf("   Красный |   %.1f   |  %3d  |  %3d%n",
                (double)sumRed / totalPixels, minRed, maxRed);
        System.out.printf("   Зеленый |   %.1f   |  %3d  |  %3d%n",
                (double)sumGreen / totalPixels, minGreen, maxGreen);
        System.out.printf("   Синий   |   %.1f   |  %3d  |  %3d%n",
                (double)sumBlue / totalPixels, minBlue, maxBlue);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("🎨 Извлечение цветовых каналов RGB");
        System.out.println("===================================");

        // Спрашиваем, использовать ли тестовое изображение
        System.out.print("\nСоздать тестовое изображение с цветными полосами? (д/н): ");
        String useTest = scanner.nextLine().trim().toLowerCase();

        BufferedImage originalImage;

        if (useTest.equals("д") || useTest.equals("да") || useTest.equals("y") || useTest.equals("yes")) {
            // Создаем тестовое изображение
            originalImage = createTestImage();
            System.out.println("✅ Создано тестовое изображение 300x200 с цветными полосами");
        } else {
            String inputFile;

            // Запрашиваем имя входного файла
            while (true) {
                System.out.print("\n📁 Введите путь к изображению: ");
                inputFile = scanner.nextLine().trim();

                if (inputFile.isEmpty()) {
                    System.out.println("❌ Путь не может быть пустым");
                    continue;
                }

                if (!fileExists(inputFile)) {
                    System.out.println("❌ Файл не найден: " + inputFile);
                    System.out.println("   Проверьте путь и попробуйте снова");
                    continue;
                }

                break;
            }

            // Загружаем изображение
            originalImage = loadImage(inputFile);
            if (originalImage == null) {
                scanner.close();
                return;
            }
        }

        // Анализируем изображение
        analyzeChannels(originalImage);

        // Спрашиваем о формате сохранения
        System.out.print("\nСохранять каналы в цвете (ц) или в градациях серого (с)? (ц/с): ");
        String formatChoice = scanner.nextLine().trim().toLowerCase();
        boolean colorMode = formatChoice.equals("ц") || formatChoice.equals("цвет");

        // Формируем базовое имя для выходных файлов
        String baseName = "test";

        System.out.println("\n🔄 Извлечение каналов...");

        // Извлекаем и сохраняем каналы
        BufferedImage redImage, greenImage, blueImage;

        if (colorMode) {
            // Цветной режим
            redImage = extractRedChannel(originalImage);
            greenImage = extractGreenChannel(originalImage);
            blueImage = extractBlueChannel(originalImage);

            System.out.println("\n💾 Сохранение цветных каналов:");
        } else {
            // Режим градаций серого - ИСПРАВЛЕНО!
            redImage = extractRedAsGrayscale(originalImage);
            greenImage = extractGreenAsGrayscale(originalImage);
            blueImage = extractBlueAsGrayscale(originalImage);

            System.out.println("\n💾 Сохранение каналов в градациях серого:");
        }

        // Сохраняем изображения
        saveImage(redImage, baseName + "_red.png");
        saveImage(greenImage, baseName + "_green.png");
        saveImage(blueImage, baseName + "_blue.png");

        System.out.println("\n✨ Готово! Все каналы сохранены.");
        System.out.println("📁 Проверьте файлы в текущей директории:");

        scanner.close();
    }
}