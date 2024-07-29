package shop.household.client.get.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import shop.household.client.get.service.ClientService;
import shop.household.model.ClientGetRequestDto;
import shop.household.model.ClientGetResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client/get")
public class RequestController {

    private final ClientService clientService;

    @PostMapping
    public ClientGetResponseDto getByFilter(@RequestHeader HttpHeaders headers, @RequestBody ClientGetRequestDto requestDto) {
        return clientService.getClients(requestDto);
    }

    @GetMapping("/{id}")
    public ClientGetResponseDto getById(@PathVariable Integer id) {
        return clientService.getById(id);
    }
}
