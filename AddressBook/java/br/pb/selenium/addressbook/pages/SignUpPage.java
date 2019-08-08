package br.pb.selenium.addressbook.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class SignUpPage {
	
	//Chamada do WebDriver
	WebDriver driver;

	//Mapeamento dos objetos que est�o sendo utilizados por cada um dos m�todos 
	By email = By.id("user_email");
	By senha = By.id("user_password");
	By btnSignUp = By.name("commit");
	By linkSignIn = By.linkText("Sign in");
	By linkSignUp = By.linkText("Sign up");
	
	public SignUpPage(WebDriver driver) {
		this.driver = driver;
	}
	
	/******************* Textos ***************************/
	public void setEmail(String strEmail) {
		driver.findElement(email).click();
		driver.findElement(email).clear();
		driver.findElement(email).sendKeys(strEmail);
	}

	public void setPassword(String strPassword) {
		driver.findElement(senha).click();
		driver.findElement(senha).clear();
		driver.findElement(senha).sendKeys(strPassword);
	}

	/******************* Bot�es ***************************/
	public void clickBtnSignIn() {
		driver.findElement(btnSignUp).click();
	}

	public void clickLinkSignIn() {
		driver.findElement(linkSignIn).click();
	}

	public void clickLinkSignUp() {
		driver.findElement(linkSignUp).click();
	}

	/****************** Verifica��es e Valida��es ********************/
	public void verificarMensagemSucesso(String vlrMensagem) {
		String getMensagem = driver.findElement(By.xpath("//h1[contains(text(), '"+ vlrMensagem +"')]")).getText();
		Assert.assertEquals(vlrMensagem, getMensagem);
	}

	public void verificarConfirmacaoEmailAcesso(String vlrEmail) {
		String getEmail = driver.findElement(By.xpath("//div/span[contains(text(), '"+ vlrEmail +"')]")).getText();
		Assert.assertEquals(vlrEmail, getEmail);
	}

	public void verificarMensagemFalha(String vlrMsgFalha) {
		String getMsgFalha = driver.findElement(By.xpath("//div[@class='alert alert-notice' and contains(text(), '"+ vlrMsgFalha +"')]")).getText();
		Assert.assertEquals(vlrMsgFalha, getMsgFalha);
	}
}