package jstart;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

/**
 * File help class utility
 */
public class OSFile {

    public enum ListOrder {
        NO_ORDER,
        ASCENDING,
        DESCENDING
    }

    /**
     * Gets the File object from string path
     * 
     * @param file string path to file or directory
     * @return File object from path
     */
    public static File of(String file) {
        return new File(file);
    }

    /**
     * Gets the File object from string parent and child path
     * 
     * @param parent string path to directory
     * @param child  strin path to file or directory into @parent
     * @return File object from path
     */
    public static File of(String parent, String child) {
        return new File(parent, child);
    }

    /**
     * Copy a file in a destination, indicating if replacing the existing file or
     * not
     * 
     * @param source      original file to copy
     * @param destination target file
     * @param replace     if true, replaces the destination file with the source
     *                    file
     * @return true if copy is successful, o false if an error ocurrs
     */
    public static boolean copy(File source, File destination, boolean replace) {
        CopyOption option = replace ? StandardCopyOption.REPLACE_EXISTING : StandardCopyOption.ATOMIC_MOVE;

        try {
            Files.copy(source.toPath(), destination.toPath(), option);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Copy a file in a destination, replacing the destination file
     * 
     * @param source      original file to copy
     * @param destination target file
     * @return true if copy is successful, o false if an error ocurrs
     */
    public static boolean copy(File source, File destination) {
        return copy(source, destination, true);
    }

    /**
     * Moves a file to a destination, indicating if replacing the existing file or
     * not
     * 
     * @param source      original file to move
     * @param destination target file
     * @param replace     if true, replaces the destination file with the source
     *                    file
     * @return true if move is successful, o false if an error ocurrs
     */
    public static boolean move(File source, File destination, boolean replace) {
        CopyOption option = replace ? StandardCopyOption.REPLACE_EXISTING : StandardCopyOption.ATOMIC_MOVE;

        try {
            Files.move(source.toPath(), destination.toPath(), option);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Moves a file in a destination, replacing the destination file
     * 
     * @param source      original file to move
     * @param destination target file
     * @return true if move is successful, o false if an error ocurrs
     */
    public static boolean move(File source, File destination) {
        return move(source, destination, true);
    }

    /**
     * Creates a path of nested directories (or one directory only)
     * 
     * @param newdirs path of directory
     * @return true if creation is successful
     */
    public static boolean mkdir(String newdirs) {
        try {
            Files.createDirectories(Path.of(newdirs));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Lists a directory content
     * 
     * @param directory     to list
     * @param noDirectories true for no list subdirectories
     * @param order         order of listing
     * @return stream of files in directory
     */
    @SuppressWarnings("resource")
    public static Stream<File> list(File directory, boolean noDirectories, ListOrder order) {
        Stream<Path> files = null;
        try {
            files = Files.list(directory.toPath());

            if (noDirectories)
                files = files.filter(file -> !Files.isDirectory(file));

            if (order == ListOrder.NO_ORDER)
                return files.map(Path::toFile);
            else
                return files.sorted((file1, file2) -> {
                    if (order == ListOrder.ASCENDING)
                        return file1.compareTo(file2);
                    else
                        return file2.compareTo(file1);
                }).map(Path::toFile);

        } catch (IOException e) {
            return Stream.empty();
        }
    }

    /**
     * Lists a directory content (including subdirectories)
     * 
     * @param directory to list
     * @param order     order of listing
     * @return stream of files in directory
     */
    public static Stream<File> list(File directory, ListOrder order) {
        return list(directory, false, order);
    }

    /**
     * Lists a directory content (including subdirectories and no order)
     * 
     * @param directory to list
     * @return stream of files in directory
     */
    public static Stream<File> list(File directory) {
        return list(directory, ListOrder.NO_ORDER);
    }

    /**
     * Deletes the content of a directory (and the directory itself)
     * 
     * @param directory to delete
     * @param inDepth   true if deletes subdirectories and subfiles
     */
    public static void rmDir(File directory, boolean inDepth) {
        if (inDepth) {
            list(directory)
                    .forEach(file -> {
                        if (file.isDirectory()) {
                            rmDir(file, inDepth);
                        } else {
                            file.delete();
                        }

                    });
        } else {
            list(directory, true, ListOrder.NO_ORDER)
                    .forEach(file -> file.delete());
        }
    }
}
