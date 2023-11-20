package com.himanshu.crud.controller;

        import com.himanshu.crud.service.DataService;
        import org.junit.jupiter.api.Test;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.boot.test.mock.mockito.MockBean;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.test.web.servlet.MockMvc;

        import static org.mockito.ArgumentMatchers.anyLong;
        import static org.mockito.Mockito.when;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DataService dataService;

    @Test
    public void testGetAllData() throws Exception {
        when(dataService.fetchTickets()).thenReturn("success");
        mockMvc.perform(get("/tickets/"))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));
    }

    @Test
    public void testGetAllTickets() throws Exception {
        when(dataService.fetchAllTickets()).thenReturn(new ResponseEntity<>("Tickets", HttpStatus.OK));
        mockMvc.perform(get("/tickets/alltickets"))
                .andExpect(status().isOk())
                .andExpect(content().string("Tickets"));
    }

    @Test
    public void testGetTicketById() throws Exception {
        when(dataService.fetchTicketById(anyLong())).thenReturn("Ticket");
        mockMvc.perform(get("/tickets/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Ticket"));
    }

    @Test
    public void testDeleteTicket() throws Exception {
        when(dataService.deleteTicketById(anyLong())).thenReturn(new ResponseEntity<>("Deleted", HttpStatus.OK));
        mockMvc.perform(delete("/tickets/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));
    }


}