package br.pb.udemycourse.rest;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class VerbosRest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = Constants.API_REST;
	}
	
	@Test
	public void deveSalvarUsuario() {
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body("{\"name\": \"Theo Paulo Samuel Moraes\", \"age\": 50}")
		.when()
			.post(Constants.API_USERS)
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Theo Paulo Samuel Moraes"))
			.body("age", is(50));
	}
	
	@Test
	public void deveSalvarUsuarioUsandoMap() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "Severino Cau� Dias");
		params.put("age", 50);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
		.when()
			.post(Constants.API_USERS)
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Severino Cau� Dias"))
			.body("age", is(50));
	}
	
	@Test
	public void deveSerializarUsuarioUsandoObjeto() {
		User user = new User("Emilly Giovana Elo� Barros", 35);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(user)
		.when()
			.post(Constants.API_USERS)
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Emilly Giovana Elo� Barros"))
			.body("age", is(35));
	}
	
	@Test
	public void deveDesserializarXMLAoSalvarUsuario() {
		User user = new User("Eduardo Pietro Pires", 40);
		
		User usuarioInserido = given()
			.log().all()
			.contentType(ContentType.XML)
			.body(user)
		.when()
			.post(Constants.API_USERS_XML)
		.then()
			.log().all()
			.statusCode(201)
			.extract().body().as(User.class);
		
		//System.out.println(usuarioInserido);
		
		Assert.assertThat(usuarioInserido.getId(), notNullValue());
		Assert.assertThat(usuarioInserido.getName(), is("Eduardo Pietro Pires"));
		Assert.assertThat(usuarioInserido.getAge(), is(40));
		Assert.assertThat(usuarioInserido.getSalary(), nullValue());
	}
	
	@Test
	public void deveDesserializarObjetoAoSalvarUsuario() {
		User user = new User("Isis Cristiane Gon�alves", 35);
		
		User usuarioInserido = given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(user)
		.when()
			.post(Constants.API_USERS)
		.then()
			.log().all()
			.statusCode(201)
			.extract().body().as(User.class);
		
		System.out.println(usuarioInserido);
		Assert.assertThat(usuarioInserido.getId(), notNullValue());
		Assert.assertEquals("Isis Cristiane Gon�alves", usuarioInserido.getName());
		Assert.assertThat(usuarioInserido.getAge(), is(35));
	}
	
	@Test
	public void naoDeveSalvarUsuarioSemNome() {
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body("{\"age\": 50}")
		.when()
			.post(Constants.API_USERS)
		.then()
			.log().all()
			.statusCode(400)
			.body("id", is(nullValue()))
			.body("error", is("Name � um atributo obrigat�rio"));
	}
	
	@Test
	public void deveSalvarUsuarioViaXML() {
		given()
			.log().all()
			.contentType(ContentType.XML)
			.body("<user><name>Theo Paulo Samuel Moraes</name><age>50</age></user>")
		.when()
			.post(Constants.API_USERS_XML)
		.then()
			.log().all()
			.statusCode(201)
			.body("user.@id", is(notNullValue()))
			.body("user.name", is("Theo Paulo Samuel Moraes"))
			.body("user.age", is("50"));
	}
	
	@Test
	public void deveSalvarUsuarioViaXMLUsandoMap() {
		User user = new User("Luiz Luan Pinto", 40);
		
		given()
			.log().all()
			.contentType(ContentType.XML)
			.body(user)
		.when()
			.post(Constants.API_USERS_XML)
		.then()
			.log().all()
			.statusCode(201)
			.body("user.@id", is(notNullValue()))
			.body("user.name", is("Luiz Luan Pinto"))
			.body("user.age", is("40"));
	}
	
	@Test
	public void deveAlterarUsuario() {
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body("{\"name\": \"Anthony Ben�cio Diogo Pinto\", \"age\": 90}")
		.when()
			.put(Constants.API_USERS + 1)
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Anthony Ben�cio Diogo Pinto"))
			.body("age", is(90))
			.body("salary", is(1234.5678f))
			;
	}
	
	@Test
	public void deveCustomizarURL() {
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body("{\"name\": \"Anthony Ben�cio Diogo Pinto\", \"age\": 90}")
		.when()
			.put("{entidade}/{userId}", "users", "1") //Mapeando as entidades
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Anthony Ben�cio Diogo Pinto"))
			.body("age", is(90))
			.body("salary", is(1234.5678f))
			;
	}
	
	@Test
	public void deveCustomizarURLParteDois() {
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body("{\"name\": \"Anthony Ben�cio Diogo Pinto\", \"age\": 90}")
			.pathParam("entidade", "users")
			.pathParam("userId", 1)
		.when()
			.put("{entidade}/{userId}") //Mapeando as entidades
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Anthony Ben�cio Diogo Pinto"))
			.body("age", is(90))
			.body("salary", is(1234.5678f))
			;
	}
	
	@Test
	public void deveRemoverUsuario() {
		given()
			.log().all()
		.when()
			.delete(Constants.API_USERS + 1)
		.then()
			.log().all()
			.statusCode(204);
	}
	
	@Test
	public void deveRemoverUsuarioInexistente() {
		given()
			.log().all()
		.when()
			.delete(Constants.API_USERS + 1000)
		.then()
			.log().all()
			.statusCode(400)
			.body("error", is("Registro inexistente"));
	}
}