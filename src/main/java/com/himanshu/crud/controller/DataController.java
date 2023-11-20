package com.himanshu.crud.controller;

import com.himanshu.crud.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class DataController {

    private final DataService dataService;

    @Autowired
    public DataController(DataService dataService){
        this.dataService = dataService;
    }


    @GetMapping("/")
    public String getAllData(){
        return dataService.fetchTickets();
    }

    @GetMapping("/alltickets")
    public ResponseEntity<String> getAllTickets(){
        return dataService.fetchAllTickets();
    }

    @GetMapping(value = "/{id}")
    public String getDataById(@PathVariable Long id){
        return dataService.fetchTicketById(id);
    }

    @DeleteMapping(value="/delete/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable Long id) {
        return dataService.deleteTicketById(id);
    }

    @GetMapping(value = "/temp/{id}")
    public ResponseEntity<String> getData(@PathVariable Long id){
        return ResponseEntity.ok("Data for ID "+id);
    }

    @GetMapping("/paginated")
    public ResponseEntity<String> getTicketsPaginated(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "25") int per_page) {
        return dataService.getData(page, per_page);
    }

}
