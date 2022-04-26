package ru.oshkin;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;

public class WebDriverHomework {
    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger(WebDriverHomework.class.getName());

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        logger.info("Драйвер поднялся");
    }

    @After
    public void finish() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void openSiteHeadLessMode() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.get("https://duckduckgo.com/");
        logger.info("Зашел на сайт");

        WebElement input = driver.findElement(By.xpath("//*[@id=\"search_form_input_homepage\"]"));
        input.sendKeys("ОТУС");
        input.submit();

        WebElement item = driver.findElement(By.xpath("//div[contains(@class,'results_links_deep')][1]/div/h2/a"));
        String text = item.getText();
        assertEquals("Онлайн‑курсы для профессионалов, дистанционное обучение ...", text);
    }

    @Test
    public void openSiteFullScreen() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--kiosk");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        logger.info("Открыли браузер в режиме киоска");

        String s = "https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free" +
                "/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818 ";
        driver.get(s);
        logger.info("Перешли по ссылке");

        WebElement closeVideo = driver.findElement(By.xpath("//*[@id='vdo_ai_cross']"));
        closeVideo.click();

        WebElement picture = driver.findElement(By.xpath("//li[@data-id='id-2']"));
        picture.click();

        WebElement modalWindow = driver.findElement(By.xpath("//div[@class='pp_hoverContainer']"));
        String actual = modalWindow.getAttribute("class");
        assertEquals("pp_hoverContainer", actual);
    }

    @Test
    public void logIn() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        String s = "https://otus.ru";
        driver.get(s);
        logger.info("Перешли по ссылке");

        WebElement button1 = driver.findElement(By.xpath("//button[@data-modal-id='new-log-reg']"));
        button1.click();

        WebElement mail = driver.findElement(By.xpath(" //input[@type='text' and @placeholder='Электронная почта']"));
        mail.sendKeys("test@mail.ru");
        logger.info("Ввели почту");

        WebElement password = driver.findElement(By.xpath("//input[@type='password' and @placeholder='Введите пароль']"));
        password.sendKeys("test123");
        logger.info("Ввели пароль");

        WebElement button = driver.findElement(By.xpath("//button[contains(text(),'Войти')]"));
        button.submit();
        logger.info("Попытка авторизации");

        Set<Cookie> cookies = driver.manage().getCookies();
        logger.info(format("Получены Cookies: %s ", cookies));
    }
}
