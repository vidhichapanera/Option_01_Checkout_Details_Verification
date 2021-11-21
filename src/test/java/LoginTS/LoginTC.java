package LoginTS;

import Config.BaseTest;
import LoginPO.Login;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class LoginTC  extends BaseTest {


    public LoginTC() {
    }

    public LoginTC(WebDriver driver, int flag) {
        this.driver = driver;
    }

    /**
     *
     * @throws IOException
     */
    @Test(description = "Login Test")
    public void loginTest() throws IOException, InterruptedException {

        String fileLocation = System.getProperty("user.dir") + "\\src\\main\\resources\\loginDetails.properties";
        Properties properties1 = new Properties();
        properties1.load(new FileReader(fileLocation));

        Login login = PageFactory.initElements(driver, Login.class);

        //Step-1 : Enter Password
        Assert.assertTrue(login.enterDataUsingSendkeys(login.getPasswordField(),properties1.getProperty("password")), "Enter Password");

        //Step-2 : Click On Enter Button
        Assert.assertTrue(login.clickOnButton(login.getEnterButton()), "Click On Enter Button");
        Thread.sleep(5000);
    }



    /**
     *
     * @throws IOException
     */
    @Test(description = "Check Out Details Verification")
    public void checkOutDetailsVerification() throws IOException, InterruptedException {

        //Pre-Condition
        loginTest();

        Login login = PageFactory.initElements(driver, Login.class);

        //Step-1 : Click on Catalog Button
        Assert.assertTrue(login.clickOnButton(login.getCatalog()), "Click on Catalog Button");

        //Step-2 : add Product to the cart
        Assert.assertTrue(login.addProductToTheCart(1), "add Product to the cart");
        Assert.assertTrue(login.addProductToTheCart(2), "add Product to the cart");

        //Step-3 : Click on cart icon
        Assert.assertTrue(login.clickOnButton(login.getCartIcon()), "Click on cart icon");

        //Step-4 : get Total Price and Product Price
        Assert.assertTrue(login.getCartPagePrice(1), "get Total Price and Product Price");
        Assert.assertTrue(login.getCartPagePrice(2), "get Total Price and Product Price");
        Assert.assertTrue(login.getCartPageTotalPrice(), "get Total Price and Product Price");

        //Step-5 : Click on checkout Button
        Assert.assertTrue(login.clickOnButton(login.getCheckoutButton()), "Click on checkout Button");

        //Step-6 : Verify Total are match
        Assert.assertTrue(login.getCheckoutPagePriceAndVerify(), "Verify Total are match");
    }


}
