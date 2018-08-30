package com.ulian168.platform.selenium.web.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.ulian168.platform.selenium.web.util.ProjectConfig;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
@Component
public class WebUIService {
    @Resource
    private ProjectConfig projectConfig;
    
    /**
     * 截取显示为图片并保存 <br>
     * 如果页面显示alert，建议将alert确认后再执行该方法
     *
     * @return 存储文件名<br>
     * @throws IOException
     */
    public String screenCapture(final WebDriver webDriver) throws IOException {
        File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        String savePigName = System.currentTimeMillis() + ".png";
        File file = new File(savePigName);
        FileUtils.copyFile(scrFile, file);
        return file.getAbsolutePath();
    }

    /**
     * 打开网页
     *
     * @param pageUrl 需要打开的URL地址
     */
    public void open(final WebDriver webDriver, final String pageUrl) {
        webDriver.get(pageUrl);

    }

    /**
     * 线程等待
     *
     * @param millis 需要等待的毫秒数
     */
    public void sleep(String millis) {
        try {
            Thread.sleep(Integer.parseInt(millis));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭全局WebDriver<br>
     * 注意：该操作会关闭全局WebDriver对象,只有在所有测试结束后才调度
     */
    public void close(final WebDriver webDriver) {
        webDriver.close();
        webDriver.quit();
    }

    /**
     * 获取Alert信息
     */
    public String getAlertTxt(final WebDriver webDriver) {
        this.sleep("100");
        Alert alert = webDriver.switchTo().alert();
        return alert.getText();
    }

    /**
     * Alert弹窗接受
     */
    public void acceptAlert(final WebDriver webDriver) {
        this.sleep("100");
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
    }

    /**
     * Alert弹窗拒绝
     */
    public void dismissAlert(final WebDriver webDriver) {
        this.sleep("100");
        Alert alert = webDriver.switchTo().alert();
        alert.dismiss();
    }

    /**
     * 关闭非首次打开窗口
     */
    public void closeNotMainWindow(final WebDriver webDriver, final String handle) {

        for (String currentWindow : webDriver.getWindowHandles())
            if (!currentWindow.equals(handle)) {
                webDriver.switchTo().window(currentWindow);
                webDriver.close();
            }
    }

    /**
     * 把driver切换到新打开的窗口
     */
    public void switchToNewWindow(final WebDriver webDriver, final String handle) {

        for (String currentWindow : webDriver.getWindowHandles())
            if (currentWindow.equals(handle)) {
                webDriver.switchTo().window(currentWindow);
            }
    }
    
    /**
     * 激活当前窗口.
     * @param webDriver
     * @param windowHandle
     */
    public void getWindowFocus(final WebDriver webDriver, String windowHandle) {
        webDriver.switchTo().window(windowHandle);
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.focus();");
        js = null;
    }

    /**
     * 跳转到指定的iframe中去
     *
     * @param iframeId 需要跳转的iframe的ID
     */
    public void switchIframe(final WebDriver webDriver, final String iframeId) {
        webDriver.switchTo().frame(iframeId);
    }

    /**
     * 从一个iframe跳转到另一个指定的iframe中去
     *
     * @param newIframeId 跳转到新iframe的ID
     */
    public void switchNewIframe(final WebDriver webDriver, String newIframeId) {

        webDriver.switchTo().defaultContent();
        webDriver.switchTo().frame(newIframeId);
    }

    /**
     * 跳转页面到指定的URL地址
     *
     * @param url 需要跳转的URL地址
     */
    public void forwardURL(final WebDriver webDriver, final String url) {
        webDriver.navigate().to(url);
    }

    /**
     * 浏览器窗口全屏显示
     */
    public void fullWindow(final WebDriver webDriver) {

        webDriver.manage().window().fullscreen();
    }

    /**
     * 浏览器窗口最大化
     */
    public void maxWindow(final WebDriver webDriver) {
        webDriver.manage().window().maximize();
    }

    /**
     * 通过元素定位返回By对象
     *
     * @param locator 元素定位标识信息(定位类型=定位值)
     * @return 返回元素定位的By对象
     */
    private By getBy(String locator) {
        String[] locaTagVal = locator.split("=", 2);
        String type = locaTagVal[0];
        String value = locaTagVal[1];

        return getEleBY(type, value);
    }

    /**
     * 获取元素对象
     *
     * @param localTag 定位类型<br>
     *                 &nbsp;&nbsp;&nbsp;&nbsp;支持八种类型（id|name|xpath|cssSelector|
     *                 className|linkText|tagName|partialLinkText）
     * @param localVal 定位值
     * @return 元素定位对象，如果构建定位对象失败返回为空
     */
    private By getEleBY(String localTag, String localVal) {
        By byObj = null;
        switch (localTag) {
            case "id":
                byObj = By.id(localVal);
                break;
            case "name":
                byObj = By.name(localVal);
                break;
            case "xpath":
                byObj = By.xpath(localVal);
                break;
            case "cssSelector":
                byObj = By.cssSelector(localVal);
                break;
            case "className":
                byObj = By.className(localVal);
                break;
            case "linkText":
                byObj = By.linkText(localVal);
                break;
            case "tagName":
                byObj = By.tagName(localVal);
                break;
            case "partialLinkText":
                byObj = By.partialLinkText(localVal);
                break;
        }
        return byObj;
    }

    /**
     * 获取元素
     *
     * @param locator 元素定位对象
     * @return 页面元素对象
     */
    private WebElement getWebEle(final WebDriver webDriver, String locator) {
        sleep("1000");
        return getUntilWaitWebEle(webDriver, getBy(locator));
    }

    /**
     * 获取元素列表
     *
     * @param locator 元素定位对象
     * @return 页面元素对象列表
     */
    private List<WebElement> getWebEles(final WebDriver webDriver, String locator) {
        return getUntilWaitWebEles(webDriver, getBy(locator));
    }

    /**
     * 智能等待获取元素
     *
     * @param byObj   元素定位对象
     * @return 页面元素对象
     */
    private WebElement getUntilWaitWebEle(final WebDriver webDriver, final By byObj) {
        return new WebDriverWait(webDriver, new Long(1))
                .until(ExpectedConditions.presenceOfElementLocated(byObj));
    }

    /**
     * 智能等待获取元素列表
     *
     * @param byObj   元素定位对象
     * @return 页面元素对象列表
     */
    private List<WebElement> getUntilWaitWebEles(final WebDriver webDriver, final By byObj) {
        return new WebDriverWait(webDriver, new Long(projectConfig.explicitWaitTimeOut))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(byObj));
    }

    /**
     * 元素点击事件
     *
     * @param locator 元素定位对象
     */
    public void click(final WebDriver webDriver, String locator) {
        getWebEle(webDriver, locator).click();
    }
    
    public void dbClick(final WebDriver webDriver, String locator) {
        Actions action = new Actions(webDriver); 
        action.doubleClick(getWebEle(webDriver, locator));
    }

    /**
     * 鼠标移动到元素
     *
     * @param locator
     */
    public void moveToEle(final WebDriver webDriver, Actions action, String locator) {
        action.moveToElement(getWebEle(webDriver, locator)).perform();
    }

    /**
     * 元素鼠标点击事件
     *
     * @param locator 元素定位对象
     */
    public void mouseclick(final WebDriver webDriver, Actions action, String locator) {
        action.click(getWebEle(webDriver, locator)).perform();
    }

    /**
     * 获取元素的类型
     *
     * @param locator 元素定位
     * @return 返回元素的类型，注意input中包括的radio、checkbox作为独立的元素类型了
     */
    private String getEleType(final WebDriver webDriver, String locator) {
        WebElement webEle = getWebEle(webDriver, locator);
        String eleType = webEle.getTagName();
        if (eleType.equals("input")) {
            String inputType = webEle.getAttribute("type");
            if (StringUtils.isBlank(inputType)) {
                switch (inputType) {
                    case "radio":
                        eleType = "radio";
                    case "checkbox":
                        eleType = "checkbox";
                }
            }
        }
        return eleType;
    }

    /**
     * 指定元素位置清除输入的值并输入指定的值
     *
     * @param locator 元素定位
     * @param keyVal   输入的值
     */
    public void sentKey(final WebDriver webDriver, String locator, String keyVal) {
        WebElement webEle = getWebEle(webDriver, locator);
        String eleType = getEleType(webDriver, locator);
        List<String> trueKey = Arrays.asList("yes", "true", "on", "checked");
        switch (eleType) {
            case "radio":
                if (trueKey.contains(keyVal) && webEle.isEnabled() != true) {
                    getWebEle(webDriver, locator).click();
                }
                break;
            case "checkbox":
                if (trueKey.contains(keyVal) && webEle.isEnabled() != true) {
                    getWebEle(webDriver, locator).click();
                }
                break;
            case "select":
                Select select = new Select(webEle);
                select.selectByVisibleText(keyVal);
                break;
            default:
                webEle.clear();
                webEle.sendKeys(keyVal);
                break;
        }
    }

    /**
     * 获取元素在页面上显示的文本信息
     *
     * @param locator 元素定位
     * @return 返回元素的文本信息
     */
    public String getValue(final WebDriver webDriver, String locator) {

        WebElement webEle = getWebEle(webDriver, locator);

        switch (webEle.getTagName()) {
            case "input":
                return webEle.getAttribute("value");
            case "radio":
                return webEle.isSelected() ? "checked" : "unchecked";
            case "checkbox":
                return webEle.isSelected() ? "checked" : "unchecked";
            case "select":
                for (int i = 0; i < getWebEles(webDriver, locator).size(); i++) {
                    WebElement selectItem = webEle.findElements(By.tagName("option")).get(i);
                    if (selectItem.isSelected())
                        return selectItem.getText();
                }
                break;
            default:
                return webEle.getText();
        }
        return null;
    }
    
    /**
     * 上传图片与文件
     * @param webDriver
     * @param locator
     * @throws Exception 
     */
    public void upload(final WebDriver webDriver, String locator, String value) {
    	//注意文件路径最后应当能解析为绝对路径，可先获取当前工程所在路径再拼接，如下所示
    	String curPath = new File("").getAbsolutePath();
    	//所得结果为D:\workspace\se2\integratedapp
    	String filePath = curPath + "\\src\\main\\resources\\file" + value;
    	//如果想要使用编译后的路径，则可使用如下方法
    	//String curPath = GetPath.class.getResource("/").toString();
    	//所得结果为file:/D:/workspace/se2/integratedapp/target/test-classes/
    	webDriver.findElement(By.xpath(locator)).sendKeys(filePath);
    }
}
