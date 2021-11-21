package LoginPO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Page-Factory Model
 */
public class Login {

    public WebDriver driver;
    public static String testPrice = "";
    public static String testTotal = "";
    public Login(WebDriver driver) {
        this.driver = driver;
    }


    /**
     * password
     */
    @FindBy(how = How.CSS, using = "input#password")
    WebElement txtbox_password;
    public WebElement getPasswordField() {
        return txtbox_password;
    }

    /**
     * enter Button
     */
    @FindBy(how = How.CSS, using = "button[type=submit]")
    WebElement btn_enter;
    public WebElement getEnterButton() {
        return btn_enter;
    }

    /**
     * catalog Button
     */
    @FindBy(how = How.XPATH, using = "//span[text()='Catalog']")
    WebElement btn_catalog;
    public WebElement getCatalog() {
        return btn_catalog;
    }


    /**
     * add to cart
     */
    @FindBy(how = How.CSS, using = "button[name=add]")
    WebElement btn_addthecart;
    public WebElement getAddToTheCartButton() {
        return btn_addthecart;
    }


    /**
     * Checkout button
     */
    @FindBy(how = How.CSS, using = "button#checkout")
    WebElement btn_checkout;
    public WebElement getCheckoutButton() {
        return btn_checkout;
    }


    /**
     * Price
     */
    @FindBy(how = How.CSS, using = "a#cart-icon-bubble")
    WebElement icon_cart;
    public WebElement getCartIcon() {
        return icon_cart;
    }

    /**
     * Total
     */
    @FindBy(how = How.CSS, using = "span.hulkapps-cart-original-total")
    WebElement txt_total;
    public WebElement getTotal() {
        return txt_total;
    }



    /**
     *
     * @param element
     * @return
     */
    public boolean clickOnButton(WebElement element) {
        try {
            WebElement button = new WebDriverWait(driver, 90).
                    until(ExpectedConditions.elementToBeClickable(element));
            Actions action = new Actions(driver);
            action.moveToElement(button).build().perform();
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].click();", button);
            Thread.sleep(5000);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    /**
     *
     * @param element
     * @param data
     * @return
     */
    public boolean enterDataUsingSendkeys(WebElement element, String data) {
        try {
            WebElement txt = new WebDriverWait(driver, 90).
                    until(ExpectedConditions.visibilityOf(element));
            txt.sendKeys(data);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }



    /**
     *
     * @return
     */
    public boolean addProductToTheCart(int index) {
        try {
            Thread.sleep(3000);
            WebElement productElements = new WebDriverWait(driver, 90).
                        until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#product-grid > li:nth-child("+index+") > div > div.card-information > div > h3 > a")));
            Thread.sleep(3000);
            clickOnButton(productElements);
            Thread.sleep(3000);
            clickOnButton(getAddToTheCartButton());
            Thread.sleep(3000);
            driver.navigate().back();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    /**
     *
     * @return
     */
    public boolean getCartPagePrice(int index) {
        try {
            Thread.sleep(3000);
            WebElement productname = new WebDriverWait(driver, 90).
                    until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#CartItem-"+index+" > td.cart-item__details > a")));
            Thread.sleep(3000);
            WebElement productPrice = new WebDriverWait(driver, 90).
                    until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#CartItem-"+index+" > td.cart-item__totals.right.small-hide > div.cart-item__price-wrapper > span > span > span")));
            testPrice += productname.getText()+"}"+productPrice.getText()+"\n";
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     *
     * @return
     */
    public boolean getCartPageTotalPrice() {
        try {
            Thread.sleep(3000);
            Actions actions = new Actions(driver);
            actions.moveToElement(getTotal()).build().perform();
            String testLine = getTotal().getText();
            testTotal = testLine.split(" ")[1];
            System.out.println(testTotal);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    /**
     *
     * @return
     */
    public boolean getCheckoutPagePriceAndVerify() {
        try {
            Thread.sleep(3000);
            WebElement price = new WebDriverWait(driver, 90).
                    until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='"+testPrice.substring(0,testPrice.length()-1).split("\n")[0].split("}")[0]+"']//parent::th//following-sibling::td[@class='product__price']//span")));
            if (price.getText().substring(1).equalsIgnoreCase(testPrice.substring(0,testPrice.length()-1).split("\n")[0].split("}")[1].split(" ")[1])) {
                System.out.println("match..../");
                WebElement price1 = new WebDriverWait(driver, 90).
                        until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='"+testPrice.substring(0,testPrice.length()-1).split("\n")[1].split("}")[0]+"']//parent::th//following-sibling::td[@class='product__price']//span")));
                if (price1.getText().substring(1).equalsIgnoreCase(testPrice.substring(0,testPrice.length()-1).split("\n")[1].split("}")[1].split(" ")[1])) {
                    System.out.println("match..../");
                    WebElement total01 = new WebDriverWait(driver, 90).
                            until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='INR']//following-sibling::span[normalize-space()]")));
                    if (total01.getText().substring(1).equalsIgnoreCase(testTotal))
                        System.out.println("--"+total01.getText()+"--");
                    else
                        System.out.println("not match..../");
                }
                else {
                    System.out.println("not match..../");
                }
            }else {
                System.out.println("not match..../");
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
