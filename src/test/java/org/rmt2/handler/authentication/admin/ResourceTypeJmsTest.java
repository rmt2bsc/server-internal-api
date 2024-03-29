package org.rmt2.handler.authentication.admin;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.dto.ResourceDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.resource.ResourceRegistryApi;
import org.modules.resource.ResourceRegistryApiException;
import org.modules.resource.ResourceRegistryApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.handler.BaseMockSingleConsumerMDBTest;
import org.rmt2.handler.authentication.SecurityMockJmsDtoData;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the resource type related JMS messages for
 * the Authentication API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class, ResourceRegistryApiFactory.class })
public class ResourceTypeJmsTest extends BaseMockSingleConsumerMDBTest {

    private static final String DESTINATION = "rmt2.queue.authentication";
    private ResourceRegistryApi mockApi;


    /**
     * 
     */
    public ResourceTypeJmsTest() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see testcases.messaging.MessageToListenerToHandlerTest#setUp()
     */
    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        mockApi = Mockito.mock(ResourceRegistryApi.class);
        PowerMockito.mockStatic(ResourceRegistryApiFactory.class);
        when(ResourceRegistryApiFactory.createWebServiceRegistryApiInstance()).thenReturn(mockApi);
        doNothing().when(this.mockApi).close();
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see testcases.messaging.MessageToListenerToHandlerTest#tearDown()
     */
    @After
    public void tearDown() throws Exception {
        return;
    }

    @Test
    public void invokeHandelrSuccess_Update() {
        String request = RMT2File.getFileContentsAsString("xml/authentication/admin/ResourceTypeUpdateRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.updateResourceType(isA(ResourceDto.class))).thenReturn(1);
        } catch (ResourceRegistryApiException e) {
            e.printStackTrace();
            Assert.fail("Resource Type update test case failed");
        }

        try {
            this.startTest();
            Mockito.verify(this.mockApi).updateResourceType(isA(ResourceDto.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }

    @Test
    public void invokeHandelrSuccess_Fetch() {
        String request = RMT2File.getFileContentsAsString("xml/authentication/admin/ResourceTypeQueryRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getResourceType(isA(ResourceDto.class)))
                    .thenReturn(SecurityMockJmsDtoData.createUserResourceTypeMockData());
        } catch (ResourceRegistryApiException e) {
            e.printStackTrace();
            Assert.fail("Resource Type update test case failed");
        }

        try {
            this.startTest();
            Mockito.verify(this.mockApi).getResourceType(isA(ResourceDto.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }

    @Test
    public void invokeHandelrSuccess_Delete() {
        String request = RMT2File.getFileContentsAsString("xml/authentication/admin/ResourceTypeDeleteRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.deleteResourceType(isA(ResourceDto.class))).thenReturn(1);
        } catch (ResourceRegistryApiException e) {
            e.printStackTrace();
            Assert.fail("Resource Type delete test case failed");
        }

        try {
            this.startTest();
            Mockito.verify(this.mockApi).deleteResourceType(isA(ResourceDto.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }

}
