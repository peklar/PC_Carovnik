package Projekt.PCCarovnik.ui;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class FunctionalUiTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
    }

    @AfterEach
    void cleanup() {
        if (driver != null) driver.quit();
    }

    private void loginAsAdmin() {
        driver.get("http://localhost:3000/login");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ime")));
        username.clear();
        username.sendKeys("alen");

        WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("geslo")));
        password.clear();
        password.sendKeys("123");

        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Login']")
        ));
        loginBtn.click();

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlToBe("http://localhost:3000/"),
                ExpectedConditions.urlToBe("http://localhost:3000")
        ));
    }

    private WebElement el(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    private void scrollCenter(WebElement e) {
        js.executeScript("arguments[0].scrollIntoView({block:'center', inline:'nearest'});", e);
    }

    private void clickWithFallback(WebElement e) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(e)).click();
        } catch (Exception ex) {
            js.executeScript("arguments[0].click();", e);
        }
    }

    private void setReactInputValue(By locator, String value) {
        WebElement e = el(locator);
        scrollCenter(e);
        clickWithFallback(e);

        js.executeScript("""
            const el = arguments[0];
            const value = arguments[1];
            el.focus();

            const tag = el.tagName.toLowerCase();
            const proto =
              tag === 'input' ? window.HTMLInputElement.prototype :
              tag === 'textarea' ? window.HTMLTextAreaElement.prototype :
              tag === 'select' ? window.HTMLSelectElement.prototype :
              null;

            if (proto) {
              const setter = Object.getOwnPropertyDescriptor(proto, 'value').set;
              setter.call(el, value);
            } else {
              el.value = value;
            }

            el.dispatchEvent(new Event('input', { bubbles: true }));
            el.dispatchEvent(new Event('change', { bubbles: true }));
        """, e, value);

        String actual = e.getAttribute("value");
        Assertions.assertEquals(value, actual, "Ni uspelo nastavit value za " + locator);
    }

    private void selectByValueReact(By selectLocator, String value) {
        WebElement s = el(selectLocator);
        scrollCenter(s);
        clickWithFallback(s);

        new Select(s).selectByValue(value);

        js.executeScript("arguments[0].dispatchEvent(new Event('change', {bubbles:true}));", s);
    }

    private void addCpuComponent(String naziv, int cena, int v1, int v2, int v3) {
        driver.get("http://localhost:3000/maintainer");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[contains(normalize-space(),'Dodajanje komponent')]")
        ));

        selectByValueReact(By.id("typeDropdown"), "CPU");

        setReactInputValue(By.id("datumIzdaje"), "2024-12-01");
        setReactInputValue(By.id("cena"), String.valueOf(cena));
        setReactInputValue(By.id("naziv"), naziv);
        setReactInputValue(By.id("opis"), "Selenium test opis");
        setReactInputValue(By.id("url"), "https://example.com");
        setReactInputValue(By.id("vrednost1"), String.valueOf(v1));
        setReactInputValue(By.id("vrednost2"), String.valueOf(v2));
        setReactInputValue(By.id("vrednost3"), String.valueOf(v3));

        WebElement submit = el(By.cssSelector("form button[type='submit']"));
        scrollCenter(submit);
        clickWithFallback(submit);

        wait.until(ExpectedConditions.attributeToBe(By.id("naziv"), "value", ""));
    }


    @Test
    void testFiltriranjeCPU() {
        loginAsAdmin();

        String naziv = "CPU-FILTER-" + System.currentTimeMillis();
        int cena = 7777;
        int v1 = 123;
        int v2 = 4567;
        int v3 = 89;

        addCpuComponent(naziv, cena, v1, v2, v3);

        String url =
                "http://localhost:3000/components?type=CPU" +
                        "&minPrice=" + cena + "&maxPrice=" + cena +
                        "&minVrednost1=" + v1 + "&maxVrednost1=" + v1 +
                        "&minVrednost2=" + v2 + "&maxVrednost2=" + v2 +
                        "&minVrednost3=" + v3 + "&maxVrednost3=" + v3;

        driver.get(url);

        WebDriverWait slowWait = new WebDriverWait(driver, Duration.ofSeconds(30));

        WebElement card = slowWait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class,'component')][.//p[starts-with(@id,'naziv') and contains(normalize-space(),'" + naziv + "')]]")
        ));

        WebElement cenaEl = card.findElement(By.xpath(".//p[starts-with(@id,'cena')]"));
        int parsedCena = Integer.parseInt(cenaEl.getText().replace("eur", "").trim());
        Assertions.assertEquals(cena, parsedCena, "Cena za NAŠO komponento ni pravilna!");

        int pv1 = Integer.parseInt(card.findElement(By.xpath(".//p[starts-with(@id,'vrednost11')]")).getText().trim());
        int pv2 = Integer.parseInt(card.findElement(By.xpath(".//p[starts-with(@id,'vrednost21')]")).getText().trim());
        int pv3 = Integer.parseInt(card.findElement(By.xpath(".//p[starts-with(@id,'vrednost31')]")).getText().trim());

        Assertions.assertEquals(v1, pv1);
        Assertions.assertEquals(v2, pv2);
        Assertions.assertEquals(v3, pv3);

    }

    @Test
    void testDodajNovoKomponentoCPU() {
        loginAsAdmin();
        driver.get("http://localhost:3000/maintainer");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[contains(normalize-space(),'Dodajanje komponent')]")
        ));

        String nazivTest = "Intel Test CPU 9000";

        selectByValueReact(By.id("typeDropdown"), "CPU");

        setReactInputValue(By.id("datumIzdaje"), "2024-12-01");
        setReactInputValue(By.id("cena"), "350");
        setReactInputValue(By.id("naziv"), nazivTest);
        setReactInputValue(By.id("opis"), "Testni opis CPU");
        setReactInputValue(By.id("url"), "https://intel.com");
        setReactInputValue(By.id("vrednost1"), "8");
        setReactInputValue(By.id("vrednost2"), "1500");
        setReactInputValue(By.id("vrednost3"), "12");

        WebElement submit = el(By.cssSelector("form button[type='submit']"));
        scrollCenter(submit);
        clickWithFallback(submit);

        WebDriverWait slowWait = new WebDriverWait(driver, Duration.ofSeconds(30));

        driver.get("http://localhost:3000/components?type=CPU&minPrice=1&maxPrice=10000" +
                "&minVrednost1=1&maxVrednost1=10000" +
                "&minVrednost2=1&maxVrednost2=20000" +
                "&minVrednost3=1&maxVrednost3=10000");

        slowWait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(normalize-space(),'" + nazivTest + "')]")
        ));

        Assertions.assertTrue(driver.getPageSource().contains(nazivTest),
                "Nova komponenta '" + nazivTest + "' ni bila najdena na /components listi.");
    }
    private WebElement configRow(String firstColContains) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class,'configuration-container')]//table//tr[td[1][contains(normalize-space(),'"
                        + firstColContains + "')]]")
        ));
    }

    private void chooseFirstOptionInRow(String firstColContains) {
        WebElement row = configRow(firstColContains);
        WebElement select = row.findElement(By.tagName("select"));

        wait.until(d -> (Boolean) (select.findElements(By.tagName("option")).size() > 1));

        Select s = new Select(select);
        s.selectByIndex(1);

        js.executeScript("arguments[0].dispatchEvent(new Event('change', {bubbles:true}));", select);
    }

    @Test
    @Order(3)
    void testShraniKonfiguracijo() {
        loginAsAdmin();
        driver.get("http://localhost:3000/configuration");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[contains(normalize-space(),'Sestavi svoj računalnik')]")
        ));

        chooseFirstOptionInRow("Grafična kartica");
        chooseFirstOptionInRow("Procesor");

        String confName = "Konf-" + System.currentTimeMillis();
        setReactInputValue(
                By.xpath("//h5[contains(normalize-space(),'Cena računalnika')]/following::input[@type='text'][1]"),
                confName
        );

        WebElement saveBtn = el(By.xpath("//button[contains(normalize-space(),'Shrani konfiguracijo')]"));
        scrollCenter(saveBtn);
        clickWithFallback(saveBtn);

        try {
            new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.alertIsPresent());
            String msg = driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();
            Assertions.fail("Alert po shranjevanju konfiguracije: " + msg);
        } catch (TimeoutException ok) {
        }
    }
}
