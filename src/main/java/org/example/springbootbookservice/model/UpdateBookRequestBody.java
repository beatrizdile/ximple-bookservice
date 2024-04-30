package org.example.springbootbookservice.model;

import lombok.*;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class UpdateBookRequestBody {
    private Optional<String> title;
    private Optional<String> author;
    private Optional<Long> isbn;
    private Optional<Integer> quantity;
}
