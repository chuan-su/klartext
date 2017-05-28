package se.klartext.app.controllers;

import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.klartext.app.elasticsearch.Word;
import se.klartext.app.elasticsearch.WordRepository;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by suchuan on 2017-05-28.
 */

@RestController
@RequestMapping(value = "/search")
public class SearchController {
    private WordRepository wordRepository;

    @Autowired
    public SearchController(WordRepository wordRepository){
        this.wordRepository = wordRepository;
    }

    @RequestMapping(value = "/words")
    public Iterable<Word> getWords(@RequestParam(value = "query",required = true) String query){
        List<Word> results =  wordRepository.findMatches(query).collect(Collectors.toList());
        return results;
    }
}
