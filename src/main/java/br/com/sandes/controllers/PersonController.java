package br.com.sandes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sandes.data.vo.v1.PersonVO;
import br.com.sandes.services.PersonServices;
import br.com.sandes.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController {

    @Autowired
    PersonServices personServices;
    
    @GetMapping(produces = {MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            "application/x-yaml"})
    @Operation(summary = "Finds all people", description = "Finds all people",
            tags = {"People"},
            responses = {
                @ApiResponse(description = "Success", responseCode = "200",
                        content = {
                                @Content(
                                        mediaType = "application/json",
                                        array = @ArraySchema(schema = @Schema(implementation = PersonVO.class)))
                        }),
                @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findAll(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "limit", defaultValue = "12") Integer limit,
        @RequestParam(value = "direction", defaultValue = "asc") String direction){

                //definindo o sortDirection
                //isso servira para indicar como os resultados da pagina serão ordenados

                //esse trecho valida se na requisição, o parametro sort direction é igual a 
                //desc, se for 'desc' os resultados serão exibidos de maneira descnedente
                //caso seja diferente disso, os valores serão exibidos de maneira ascendente;
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
    	
        //defiinindo a numero de paginas, a quantidade de itens na pagina e a ordenação
        //se ascendente ou descendente;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));

    	return ResponseEntity.ok(personServices.findAll(pageable));
    }

    @CrossOrigin(origins = "http://localhost:8080") //como isso, apenas os acessos desse endereço poderao acessar esse endoint;
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML})
    @Operation(summary = "Finds a person", description = "Finds a person",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content =
                                    @Content(schema = @Schema(implementation = PersonVO.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            }
    )
    public PersonVO findById(@PathVariable(value = "id") Long id) {

        return personServices.findById(id);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://erudio.com.br"}) //como isso, apenas os acessos desse endereço poderao acessar esse endoint;
    @PostMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
                 consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Adds a new person", description = "Adds a new person by passingn in a JSON, XML or YML representation of the person.",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content =
                            @Content(schema = @Schema(implementation = PersonVO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            }
    )
    public PersonVO create(@RequestBody PersonVO person) {
        return personServices.create(person);
    }
    
    @PutMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
                consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Updates a person by it's id", description = "Adds a new person by passingn in a JSON, XML or YML representation of the person.",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content =
                            @Content(schema = @Schema(implementation = PersonVO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            }
    )
    public PersonVO update(@RequestBody PersonVO person) {
    	return personServices.update(person);
    }
    
    @PatchMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML})
    @Operation(summary = "Disable a specif Person by id!", description = "Disable a Person",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content =
                                    @Content(schema = @Schema(implementation = PersonVO.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            }
    )
    public PersonVO disblePerson(@PathVariable(value = "id") Long id) {

        return personServices.disablePerson(id);
    }
    
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletes a person", description = "Adds a new person by passingn in a JSON, XML or YML representation of the person.",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        personServices.delete(id);

    	return ResponseEntity.noContent().build();
    }

}