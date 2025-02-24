package org.camunda.bpm.extension.hooks.listeners;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.extension.hooks.listeners.data.Application;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.extension.commons.connector.HTTPServiceInvoker;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Properties;

/**
 * Test class for ApplicationAuditListener
 */
@ExtendWith(SpringExtension.class)
public class ApplicationAuditListenerTest {

	@InjectMocks
	private ApplicationAuditListener applicationAuditListener;

	@Mock
	private HTTPServiceInvoker httpServiceInvoker;

	/**
	 * Application Audit Listener will be invoked with DelegateExecution parameter
	 * and success
	 */
	@Test
	public void invokeApplicationAuditService_with_delegateExecution_with_success() throws IOException {
		DelegateExecution delegateExecution = mock(DelegateExecution.class);
		String formUrl = "http://localhost:3001/form/id1";
		String apiUrl = "http://localhost:5000";
		String applicationStatus = "Success";
		Properties properties = mock(Properties.class);
		when(httpServiceInvoker.getProperties())
				.thenReturn(properties);
		when(properties.getProperty("api.url"))
				.thenReturn(apiUrl);
		when(delegateExecution.getVariable("formUrl")).thenReturn(formUrl);
		when(delegateExecution.getVariable("applicationStatus")).thenReturn(applicationStatus);
		when(delegateExecution.getVariable("applicationId")).thenReturn("id1");
		ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
		when(httpServiceInvoker.execute(any(), any(HttpMethod.class), any(Application.class)))
				.thenReturn(responseEntity);
		applicationAuditListener.notify(delegateExecution);
	}

	/**
	 * Application Audit Listener will be invoked with DelegateExecution parameter
	 * and expecting a status code as 500
	 */
	@Test
	public void invokeApplicationAuditService_with_delegateExecution_with_statusCode500() throws IOException {
		DelegateExecution delegateExecution = mock(DelegateExecution.class);
		String formUrl = "http://localhost:3001/form/id1";
		String apiUrl = "http://localhost:5000";
		String applicationStatus = "Success";
		Properties properties = mock(Properties.class);
		when(httpServiceInvoker.getProperties())
				.thenReturn(properties);
		when(properties.getProperty("api.url"))
				.thenReturn(apiUrl);
		when(delegateExecution.getVariable("formUrl")).thenReturn(formUrl);
		when(delegateExecution.getVariable("applicationStatus")).thenReturn(applicationStatus);
		when(delegateExecution.getVariable("applicationId")).thenReturn("id1");
		ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		when(httpServiceInvoker.execute(any(), any(HttpMethod.class), any(Application.class)))
				.thenReturn(responseEntity);
		assertThrows(RuntimeException.class, () -> {
			applicationAuditListener.notify(delegateExecution);
		});
	}

	/**
	 * Application Audit Listener will be invoked with DelegateExecution parameter
	 * and expecting IOException
	 */
	@Test
	public void invokeApplicationAuditService_with_delegateExecution_with_ioException() throws IOException {
		DelegateExecution delegateExecution = mock(DelegateExecution.class);
		String formUrl = "http://localhost:3001/form/id1";
		String apiUrl = "http://localhost:5000";
		String applicationStatus = "Success";
		Properties properties = mock(Properties.class);
		when(httpServiceInvoker.getProperties())
				.thenReturn(properties);
		when(properties.getProperty("api.url"))
				.thenReturn(apiUrl);
		when(delegateExecution.getVariable("formUrl")).thenReturn(formUrl);
		when(delegateExecution.getVariable("applicationStatus")).thenReturn(applicationStatus);
		when(delegateExecution.getVariable("applicationId")).thenReturn("id1");
		doThrow(new IOException("Unable to read submission for: " +formUrl)).
				when(httpServiceInvoker).execute(any(), any(HttpMethod.class), any(Application.class));
		assertThrows(RuntimeException.class, () -> {
			applicationAuditListener.notify(delegateExecution);
		});
	}

	/**
	 * Application Audit Listener will be invoked with DelegateTask parameter and
	 * success
	 */
	@Test
	public void invokeApplicationAuditService_with_delegateTask_with_success() throws IOException {
		DelegateTask delegateTask = mock(DelegateTask.class);
		DelegateExecution delegateExecution = mock(DelegateExecution.class);
		String formUrl = "http://localhost:3001/form/id1";
		String apiUrl = "http://localhost:5000";
		String applicationStatus = "Success";
		when(delegateTask.getExecution())
				.thenReturn(delegateExecution);
		Properties properties = mock(Properties.class);
		when(httpServiceInvoker.getProperties())
				.thenReturn(properties);
		when(properties.getProperty("api.url"))
				.thenReturn(apiUrl);
		when(delegateExecution.getVariable("formUrl")).thenReturn(formUrl);
		when(delegateExecution.getVariable("applicationStatus")).thenReturn(applicationStatus);
		when(delegateExecution.getVariable("applicationId")).thenReturn("id1");
		ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
		when(httpServiceInvoker.execute(any(), any(HttpMethod.class), any(Application.class)))
				.thenReturn(responseEntity);
		applicationAuditListener.notify(delegateTask);
	}
	
	/**
	 * Application Audit Listener will be invoked with DelegateTask parameter and
	 * expecting a status code as 500
	 */
	@Test
	public void invokeApplicationAuditService_with_delegateTask_with_statusCode500() throws IOException {
		DelegateTask delegateTask = mock(DelegateTask.class);
		DelegateExecution delegateExecution = mock(DelegateExecution.class);
		String formUrl = "http://localhost:3001/form/id1";
		String apiUrl = "http://localhost:5000";
		String applicationStatus = "Success";
		when(delegateTask.getExecution())
				.thenReturn(delegateExecution);
		Properties properties = mock(Properties.class);
		when(httpServiceInvoker.getProperties())
				.thenReturn(properties);
		when(properties.getProperty("api.url"))
				.thenReturn(apiUrl);
		when(delegateExecution.getVariable("formUrl")).thenReturn(formUrl);
		when(delegateExecution.getVariable("applicationStatus")).thenReturn(applicationStatus);
		when(delegateExecution.getVariable("applicationId")).thenReturn("id1");
		ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		when(httpServiceInvoker.execute(any(), any(HttpMethod.class), any(Application.class)))
				.thenReturn(responseEntity);
		assertThrows(RuntimeException.class, () -> {
			applicationAuditListener.notify(delegateTask);
		});
	}

	/**
	 * Application Audit Listener will be invoked with DelegateTask parameter and
	 * expecting IOException
	 */
	@Test
	public void invokeApplicationAuditService_with_delegateTask_with_ioException() throws IOException {
		DelegateTask delegateTask = mock(DelegateTask.class);
		DelegateExecution delegateExecution = mock(DelegateExecution.class);
		String formUrl = "http://localhost:3001/form/id1";
		String apiUrl = "http://localhost:5000";
		String applicationStatus = "Success";
		when(delegateTask.getExecution())
				.thenReturn(delegateExecution);
		Properties properties = mock(Properties.class);
		when(httpServiceInvoker.getProperties())
				.thenReturn(properties);
		when(properties.getProperty("api.url"))
				.thenReturn(apiUrl);
		when(delegateExecution.getVariable("formUrl")).thenReturn(formUrl);
		when(delegateExecution.getVariable("applicationStatus")).thenReturn(applicationStatus);
		when(delegateExecution.getVariable("applicationId")).thenReturn("id1");
		ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		doThrow(new IOException("Unable to read submission for: " +formUrl)).
				when(httpServiceInvoker).execute(any(), any(HttpMethod.class), any(Application.class));
		assertThrows(RuntimeException.class, () -> {
			applicationAuditListener.notify(delegateTask);
		});
	}
}
