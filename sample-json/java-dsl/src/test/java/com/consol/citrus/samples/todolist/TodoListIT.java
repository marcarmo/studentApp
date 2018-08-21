/*
 * Copyright 2006-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.consol.citrus.samples.todolist;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.dsl.testng.TestNGCitrusTestDesigner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

/**
 * @author Christoph Deppisch
 */
public class TodoListIT extends TestNGCitrusTestDesigner {

    @Autowired
    private HttpClient todoStudent;
      
    @Test
	@CitrusTest
    public void testJsonPayloadGetValidation() {
        http()
            .client(todoStudent)
            .send()
            .get("/student/list");

        http()
            .client(todoStudent)
            .receive()
            .response(HttpStatus.OK);
    }
    
    
    @Test
	@CitrusTest
    public void testJsonPayloadPostValidation() {
    	    	
	    variable("strName", "citrus:randomString(8)");
	    variable("strLastName", "citrus:randomString(8)");
	    variable("strEmail", "citrus:concat(citrus:randomString(10),'@losgmail.com')");
	    variable("strProgramme","2" );
	    variable("strCourses", "[\"curso1\",\"curso2\"]" );
   	
    //	obj.variablesTestJson();
        
        http()
            .client(todoStudent)
            .send()
            .post("/student")
            .messageType(MessageType.JSON)
            .contentType(ContentType.APPLICATION_JSON.getMimeType())
            .payload("{ \"firstName\": \"${strName}\", \"lastName\": \"${strLastName}\", \"email\": \"${strEmail}\", \"programme\": ${strProgramme},\"courses\": ${strCourses}}");

        http()
            .client(todoStudent)
            .receive()
            .response(HttpStatus.CREATED)
            .messageType(MessageType.JSON);
    }
    

    @Test
	@CitrusTest
    public void testJsonPayloadPutValidation() {
        variable("strName", "citrus:randomUUID()");
        variable("strLastName", "citrus:concat('todo_', citrus:randomNumber(4))");
        variable("strEmail", "citrus:concat(citrus:randomNumber(4),'@losgmail.com')");
        variable("strProgramme","1" );
        variable("strCourses", "true");

        
       http()
            .client(todoStudent)
            .send()
            .put("/student/5")
            .messageType(MessageType.JSON)
            .contentType(ContentType.APPLICATION_JSON.getMimeType())
            .payload("{ \"firstName\": \"${strName}\", \"lastName\": \"${strLastName}\", \"email\": \"${strEmail}\", \"programme\": ${strProgramme},\"strCourses\": ${strCourses}}");

        http()
            .client(todoStudent)
            .receive()
            .response(HttpStatus.OK)
            .messageType(MessageType.JSON);

    }


    @Test
	@CitrusTest
    public void testJsonPayloadPatchValidation() {
        
    	variable("strEmail", "citrus:concat(citrus:randomString(4),'@losgmail.com')");
    	
       http()
            .client(todoStudent)
            .send()
            .patch("/student/5")
            .messageType(MessageType.JSON)
            .contentType(ContentType.APPLICATION_JSON.getMimeType())
            .payload("{ \"email\": \"${strEmail}\"}");

        http()
            .client(todoStudent)
            .receive()
            .response(HttpStatus.OK)
            .messageType(MessageType.JSON);

    }

    @Test
	@CitrusTest
    public void testJsonPayloadDeleteValidation() {
        
    	variable("strEmail", "citrus:concat(citrus:randomNumber(4),'@losgmail.com')");
    	
       http()
            .client(todoStudent)
            .send()
            .delete("/student/100");

        http()
            .client(todoStudent)
            .receive()
            .response(HttpStatus.NO_CONTENT)
            .messageType(MessageType.JSON);

    }
    

}
