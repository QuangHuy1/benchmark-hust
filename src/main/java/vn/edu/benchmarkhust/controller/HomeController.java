package vn.edu.benchmarkhust.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class HomeController {

    private final String appDir;
    private final BuildProperties buildProperties;

    public HomeController(@Value("${app.dir:.}") String appDir, BuildProperties buildProperties) {
        this.appDir = appDir;
        this.buildProperties = buildProperties;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> health = new HashMap<>();
        health.put("status", new Health.Builder().up().build().getStatus().toString());
        health.put("version", buildProperties.getVersion());
        return health;
    }

    @RequestMapping("/file")
    public void downloadFile(HttpServletResponse response, @RequestParam("fileName") String fileName) {
        log.info("Download file " + fileName);
        File file = new File(appDir + File.separator + fileName);
        log.info("File " + file.getAbsolutePath() + " - " + file.exists() + " - " + file.length());
        if (!file.exists()) return;
        if (fileName.endsWith(".gz")) {
            response.setContentType("application/x-gzip");
        } else {
            response.setContentType("text/plain");
        }
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        try {
            Files.copy(file.toPath(), response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException ex) {
            log.error(ex.toString(), ex);
        }
    }
}
