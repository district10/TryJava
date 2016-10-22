package com.tangzhixiong.TryJava;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by tzx on 2016/10/21.
 */
public class FileDemo {
    public static void main(String[] args)  throws IOException {
        // testFile();
        // testFilter();
        // testTryLock();
        // testFileVisitor();
        // testWatchService();
        // testFileAttribute();
    }

    public static void testFileAttribute() throws IOException {
        // TODO
		// 获取将要操作的文件
		Path testPath = Paths.get("README.md");
		// 获取访问基本属性的BasicFileAttributeView
		BasicFileAttributeView basicView = Files.getFileAttributeView(testPath , BasicFileAttributeView.class);
		// 获取访问基本属性的BasicFileAttributes
		BasicFileAttributes basicAttribs = basicView.readAttributes();
		// 访问文件的基本属性
		System.out.println("创建时间：" + new Date(basicAttribs.creationTime().toMillis()));
		System.out.println("最后访问时间：" + new Date(basicAttribs.lastAccessTime().toMillis()));
		System.out.println("最后修改时间：" + new Date(basicAttribs.lastModifiedTime().toMillis()));
		System.out.println("文件大小：" + basicAttribs.size());
		// 获取访问文件属主信息的FileOwnerAttributeView
		FileOwnerAttributeView ownerView = Files.getFileAttributeView(testPath, FileOwnerAttributeView.class);
		// 获取该文件所属的用户
		System.out.println(ownerView.getOwner());
		// 获取系统中guest对应的用户
		UserPrincipal user = FileSystems.getDefault().getUserPrincipalLookupService().lookupPrincipalByName("guest");
		// 修改用户
		ownerView.setOwner(user);
		// 获取访问自定义属性的FileOwnerAttributeView
		UserDefinedFileAttributeView userView = Files.getFileAttributeView(testPath, UserDefinedFileAttributeView.class);
		List<String> attrNames = userView.list();
		// 遍历所有的自定义属性
		for (String name : attrNames) {
			ByteBuffer buf = ByteBuffer.allocate(userView.size(name));
			userView.read(name, buf);
			buf.flip();
			String value = Charset.defaultCharset().decode(buf).toString();
			System.out.println(name + "--->" + value) ;
		}
		// 添加一个自定义属性
		userView.write("发行者", Charset.defaultCharset().encode("疯狂Java联盟"));
		// 获取访问DOS属性的DosFileAttributeView
		DosFileAttributeView dosView = Files.getFileAttributeView(testPath, DosFileAttributeView.class);
		// 将文件设置隐藏、只读
		dosView.setHidden(true);
		dosView.setReadOnly(true);
    }

    public static void testWatchService() {
        try {
            // 获取文件系统的 WatchService 对象
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Paths.get(".").register(watchService
                    , StandardWatchEventKinds.ENTRY_CREATE
                    , StandardWatchEventKinds.ENTRY_MODIFY
                    , StandardWatchEventKinds.ENTRY_DELETE);
            while (true) {
                WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    System.out.println(event.context() +" 文件发生了 " + event.kind()+ "事件！");
                }
                // 重设WatchKey
                boolean valid = key.reset();
                // 如果重设失败，退出监听
                if (!valid) {
                    break;
                }
            }
        }
        catch (Exception e ) {
            e.printStackTrace();
        }
    }

    public static void testFileVisitor(){
        try {
            // Files.walkFileTree(Paths.get("g:", "publish" , "codes" , "15")
            Files.walkFileTree(Paths.get("."), new SimpleFileVisitor<Path>() {
                // 访问文件时候触发该方法
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println("正在访问：（" + file+"）文件");
                    // 找到了FileInputStreamTest.java文件
                    if (file.endsWith("FileDemo.java")) {
                        System.out.println("--已经找到目标文件--");
                        return FileVisitResult.TERMINATE;
                    }
                    return FileVisitResult.CONTINUE;
                }

                // 开始访问目录时触发该方法
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    System.out.println("正在访问：（" + dir + "）路径");
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testTryLock() {
        try (
                // 使用FileOutputStream获取FileChannel
                FileChannel channel = new FileOutputStream("README.md.bak").getChannel();
        ) {
            FileLock lock = channel.tryLock();  // 使用非阻塞式方式对指定文件加锁
            Thread.sleep(20_000);                // 程序暂停 20 s
            lock.release();                     // 释放锁
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testFilter() {
        // File file = new File("./com/tangzhixiong/TryJava/");
        File file = new File(".");
        // 使用 Lambda 表达式（目标类型为 FilenameFilter）实现文件过滤器。
        // 如果文件名以 .java 结尾，或者文件对应一个路径，返回 true
        String[] nameList = file.list((dir, name) -> name.endsWith(".java") || new File(name).isDirectory());
        System.out.println(Arrays.toString(nameList));
    }

    public static void testFile() throws IOException {
        // 以当前路径来创建一个 File 对象
        File file = new File(".");
        System.out.println(file.getName());
        System.out.println(file.getParent());                   // 获取相对路径的父路径可能出错，下面代码输出null
        System.out.println(file.getAbsoluteFile());             // 获取绝对路径
        System.out.println(file.getAbsoluteFile().getParent()); // 获取上一级路径

        // 在当前路径下创建一个临时文件
        File tmpFile = File.createTempFile("aaa", ".txt", file);
        // 指定当 JVM 退出时删除该文件
        tmpFile.deleteOnExit();

        // 以系统当前时间作为新文件名来创建新文件
        File newFile = new File(System.currentTimeMillis() + "");
        System.out.println("newFile 对象是否存在：" + newFile.exists());
        newFile.createNewFile();
        newFile.mkdir();                                        // 已经有文件了，就不能创建目录

        // 使用 list() 方法来列出当前路径下的所有文件和路径
        String[] fileList = file.list();
        System.out.println("当前路径下所有文件和路径如下："+Arrays.toString(fileList));

        // listRoots() 静态方法列出所有的磁盘根路径。
        File[] roots = File.listRoots();
        System.out.println("系统所有根路径如下："+Arrays.toString(roots));
    }
}
