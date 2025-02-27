package icoding.springboot.cardetect.utils;/* I love coding */

import java.io.*;
import java.time.LocalDate;

public class MYSQL_ {

    // 定义MySQL相关信息
    private static final String DB_HOST = "localhost"; // 数据库主机
    private static final String DB_PORT = "3306";      // 数据库端口
    private static final String DB_NAME = "car_detect defect imgs"; // 数据库名和表名
    private static final String DB_USER = "root";       // 数据库用户名
    private static final String DB_PASSWORD = "1234";   // 数据库密码
    private static final String BACKUP_PATH = "./backup_data/"+LocalDate.now()+".sql";    // 备份文件路径

    public static void backup(){
        try {
            // 构建mysqldump命令
            StringBuilder command = new StringBuilder();
            command.append("mysqldump -h ").append(DB_HOST)
                    .append(" -P ").append(DB_PORT)
                    .append(" -u ").append(DB_USER)
                    .append(" -p").append(DB_PASSWORD) // 注意：密码与-p之间没有空格
                    .append(" ").append(DB_NAME);

            // 执行命令并获取输出流
            Process process = Runtime.getRuntime().exec(command.toString());
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // 创建备份文件
            File backupFile = new File(BACKUP_PATH);
            FileWriter writer = new FileWriter(backupFile);

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + System.lineSeparator());
            }

            // 关闭资源
            writer.close();
            reader.close();

            // 检查命令执行状态
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("数据库备份成功！备份文件路径：" + BACKUP_PATH);
            } else {
                System.err.println("数据库备份失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int restore(LocalDate date) {
        final String DB_HOST = "localhost"; // 数据库主机
        final String DB_PORT = "3306";      // 数据库端口
        final String DB_NAME = "car_detect"; // 数据库名称
        final String DB_USER = "root";       // 数据库用户名
        final String DB_PASSWORD = "1234";   // 数据库密码
        final String BACKUP_PATH = "./backup_data/" + date + ".sql";    // 备份文件路径
        try {
            // 构建 mysql 命令（不包含 < 符号）
            String[] command = {"mysql", "-h", DB_HOST, "-P", DB_PORT, "-u", DB_USER, "-p" + DB_PASSWORD, DB_NAME};

            System.out.println("执行的命令: " + String.join(" ", command));

            // 启动进程
            Process process = new ProcessBuilder(command).start();

            // 检查 SQL 文件是否存在
            File sqlFile = new File(BACKUP_PATH);
            if (!sqlFile.exists()) {
                System.err.println("SQL 文件不存在: " + sqlFile.getAbsolutePath());
                return -1;
            }

            // 将 SQL 文件内容写入 mysql 的标准输入
            try (InputStream fileInputStream = new FileInputStream(sqlFile);
                 OutputStream processOutputStream = process.getOutputStream()) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    processOutputStream.write(buffer, 0, bytesRead);
                }
                processOutputStream.flush(); // 确保所有数据都被写入
            }

            // 启动线程读取标准输出
            Thread outputThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("标准输出: " + line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            outputThread.start();

            // 启动线程读取错误输出
            Thread errorThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.err.println("错误输出: " + line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            errorThread.start();

            // 等待线程完成
            outputThread.join();
            errorThread.join();

            // 等待进程结束
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("数据库恢复成功！");
                return 1;
            } else {
                System.err.println("数据库恢复失败！退出码：" + exitCode);
                return 0;
            }
        } catch (Exception e) {

            e.printStackTrace();
            return 0;
        }
    }

}
