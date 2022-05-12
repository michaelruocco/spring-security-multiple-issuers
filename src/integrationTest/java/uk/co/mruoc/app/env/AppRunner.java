package uk.co.mruoc.app.env;

import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import uk.co.mruoc.app.App;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppRunner {

    private static ConfigurableApplicationContext context;

    public static void startAppIfNotStarted(AppExtensionConfig config) {
        if (isStarted()) {
            log.info("app already started so not starting");
        }
        String[] args = config.getArgs();
        log.info("starting app with arg {}", Arrays.toString(args));
        context = SpringApplication.run(App.class, args);
        waitForStartToComplete(config.getAppPort());
    }

    public static void shutdownIfStarted() {
        if (!isStarted()) {
            log.info("app not started so cannot shutdown");
            return;
        }
        log.info("shutting down app");
        SpringApplication.exit(context, () -> 0);
    }

    private static boolean isStarted() {
        return Objects.isNull(context);
    }

    private static void waitForStartToComplete(int port) {
        log.info("waiting for app startup to complete...");
        await().dontCatchUncaughtExceptions()
                .atMost(Duration.ofSeconds(15))
                .pollInterval(Duration.ofMillis(250))
                .until(PortReady.local(port));
    }
}
