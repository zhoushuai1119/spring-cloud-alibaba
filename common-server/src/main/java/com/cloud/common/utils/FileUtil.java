package com.cloud.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-07-08 10:35
 */
@Slf4j
public class FileUtil {

    /**
     * 文件拷贝
     *
     * @param sourcePath 资源文件路径
     * @param targetPath 目标文件路径
     * @param isReplace  如果目标文件已存在是否强制替换
     */
    public static void fileCopy(String sourcePath, String targetPath, Boolean isReplace) throws IOException {
        if (Objects.nonNull(sourcePath) && Objects.nonNull(targetPath)) {
            Path sourceFilePath = Paths.get(sourcePath);
            Path targetFilePath = Paths.get(targetPath);
            if (isReplace) {
                Files.copy(sourceFilePath, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.copy(sourceFilePath, targetFilePath);
            }
        }
    }


    /**
     * 文件移动
     *
     * @param sourcePath 资源文件路径
     * @param targetPath 目标文件路径
     */
    public static void fileMove(String sourcePath, String targetPath) throws IOException {
        if (Objects.nonNull(sourcePath) && Objects.nonNull(targetPath)) {
            Path sourceFilePath = Paths.get(sourcePath);
            Path targetFilePath = Paths.get(targetPath);
            Files.move(sourceFilePath, targetFilePath, StandardCopyOption.ATOMIC_MOVE);
        }
    }

    /**
     * 删除目录及目录下的所有文件
     *
     * @param directoryPath
     */
    public static void delFileDirectory(String directoryPath) throws IOException {
        if (Objects.nonNull(directoryPath)) {
            Path fileDirectoryPath = Paths.get(directoryPath);
            Files.walkFileTree(fileDirectoryPath, new SimpleFileVisitor<Path>() {

                /**
                 * 访问文件时调用
                 */
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return super.visitFile(file, attrs);
                }

                /**
                 * 退出目录时调用
                 */
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return super.postVisitDirectory(dir, exc);
                }
            });
        }
    }


    /**
     * 拷贝目录及目录下的所有文件
     *
     * @param sourceDirectoryPath 资源目录路径
     * @param targetDirectoryPath 目标目录路径
     */
    public static void copyFileDirectory(String sourceDirectoryPath, String targetDirectoryPath) throws IOException {
        if (Objects.nonNull(sourceDirectoryPath) && Objects.nonNull(targetDirectoryPath)) {
            Files.walk(Paths.get(sourceDirectoryPath)).forEach(path -> {
                try {
                    String targetDic = path.toString().replace(sourceDirectoryPath, targetDirectoryPath);
                    //如果是目录，则新建目标目录的路径
                    if (Files.isDirectory(path)) {
                        Files.createDirectories(Paths.get(targetDic));
                    //如果是普通文件，则拷贝
                    } else if (Files.isRegularFile(path)) {
                        Files.copy(path,Paths.get(targetDic));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }


    /**
     * 遍历目录下的所有文件和目录
     * @param directoryPath
     */
    public static void getDirectoryFileList(String directoryPath) throws IOException {
        if (Objects.nonNull(directoryPath)) {
            Path fileDirectoryPath = Paths.get(directoryPath);
            AtomicInteger dirCount = new AtomicInteger();
            AtomicInteger fileCount = new AtomicInteger();
            Files.walkFileTree(fileDirectoryPath, new SimpleFileVisitor<Path>() {

                /**
                 * 进入目录时调用
                 */
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    dirCount.incrementAndGet();
                    return super.preVisitDirectory(dir, attrs);
                }

                /**
                 * 访问文件时调用
                 */
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    fileCount.incrementAndGet();
                    return super.visitFile(file, attrs);
                }

            });
            log.info("目录{}下有{}个目录，{}个文件",directoryPath,dirCount,fileCount);
        }
    }
}
