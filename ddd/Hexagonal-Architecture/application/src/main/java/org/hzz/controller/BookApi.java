package org.hzz.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hzz.data.BookDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("/books")
@Tag(name = "Book API")
@Validated
public interface BookApi {

    @Operation(summary = "Add a new book")
    @ApiResponse(responseCode = "200", description = "Book created")
    @PostMapping("/add")
    BookDto addBook(
                    @Parameter(description = "Book to add. Cannot null or empty.",
                            required = true)
                    @RequestBody
                    BookDto bookDto);

    @Operation(summary = "Update an existing book")
    @ApiResponse(responseCode = "200", description = "Book updated")
    @PutMapping("/update")
    BookDto updateBook(
            @Parameter(description = "Book to update. Cannot null or empty.",
                    required = true)
            @RequestBody BookDto bookDto);


    @Operation(summary = "get a book")
    @ApiResponse(responseCode = "200", description = "Book retrieved")
    @GetMapping("/get/{id}")
    BookDto getBookByID(
            @Parameter(description = "Book id to retrieve. Cannot be empty.",
                    required = true,example = "1")
            @PathVariable(name = "id")
            @NotNull long id);

    @Operation(summary = "get book list")
    @ApiResponse(responseCode = "200", description = "Book list retrieved")
    @GetMapping("/get")
    List<BookDto> getAllBooks();

    @Operation(summary = "delete a book")
    @ApiResponse(responseCode = "200", description = "Book deleted")
    @DeleteMapping("/delete/{id}")
    void deleteBookByID(
            @Parameter(description = "Book id to delete. Cannot be empty.",
                    required = true,example = "1")
            @PathVariable
            @NotNull long id);
}
