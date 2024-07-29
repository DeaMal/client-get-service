package shop.household.client.get.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import shop.household.client.get.service.ClientService;
import shop.household.model.ClientCreateRequestDto;
import shop.household.model.ClientCreateResponseDto;
import shop.household.model.ErrorDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client/get")
public class RequestController {

    private final ClientService clientService;

    @PostMapping
    public ClientCreateResponseDto getByFilter(@RequestHeader HttpHeaders headers, @RequestBody ClientCreateRequestDto requestDto) {
        try {
            return clientService.getClients(requestDto); // TODO ClientGetRequestDto, ClientGetResponseDto
        } catch (Exception e) {
            return new ClientCreateResponseDto()
                    .status(false)
                    .error(new ErrorDto().code(422).message(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ClientCreateResponseDto getById(@PathVariable Integer id) {
        try {
            return clientService.getById(id);
        } catch (Exception e) {
            return new ClientCreateResponseDto()
                    .status(false)
                    .error(new ErrorDto().code(422).message(e.getMessage()));
        }
    }
}
