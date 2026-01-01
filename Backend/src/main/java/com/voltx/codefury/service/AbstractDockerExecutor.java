package com.voltx.codefury.service;

import com.voltx.codefury.dto.ExecutionResult;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class AbstractDockerExecutor {

    protected ExecutionResult runInDocker(
            String image,
            String compileCmd,
            String runCmd,
            Map<String, String> files
    ) {

        Path dir = null;

        try {
            dir = Files.createTempDirectory("exec-");

            // write files
            for (var e : files.entrySet()) {
                Files.writeString(dir.resolve(e.getKey()), e.getValue());
            }

            String cmd = String.format(
                "docker run --rm " +
                "-m 256m --cpus=\"0.5\" " +
                "--network none " +
                "--pids-limit 64 " +
                "--security-opt no-new-privileges " +
                "--cap-drop ALL " +
                "-v \"%s:/workspace\" " +
                "%s " +
                "\"%s %s\"",
                dir.toAbsolutePath(),
                image,
                compileCmd,
                runCmd
            );

            Process p = new ProcessBuilder("cmd", "/c", cmd).start();

            boolean finished = p.waitFor(5, TimeUnit.SECONDS);
            if (!finished) {
                p.destroyForcibly();
                return ExecutionResult.timeout();
            }

            if (Files.exists(dir.resolve("ce.txt")) &&
                Files.size(dir.resolve("ce.txt")) > 0) {
                return ExecutionResult.compileError(
                    Files.readString(dir.resolve("ce.txt"))
                );
            }

            if (Files.exists(dir.resolve("re.txt")) &&
                Files.size(dir.resolve("re.txt")) > 0) {
                return ExecutionResult.runtimeError(
                    Files.readString(dir.resolve("re.txt"))
                );
            }

            return ExecutionResult.success(
                Files.readString(dir.resolve("out.txt"))
            );

        } catch (Exception e) {
            return ExecutionResult.systemError(e.getMessage());
        } finally {
            if (dir != null) {
                try {
                    Files.walk(dir)
                         .sorted(Comparator.reverseOrder())
                         .map(Path::toFile)
                         .forEach(File::delete);
                } catch (Exception ignored) {}
            }
        }
    }
}
