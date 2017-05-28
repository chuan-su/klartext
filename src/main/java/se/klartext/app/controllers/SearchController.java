package se.klartext.app.controllers;

import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by suchuan on 2017-05-28.
 */

@RestController
@RequestMapping(value = "/search")
public class SearchController {
    private TransportClient es;

    @Autowired
    public SearchController(TransportClient es){
        this.es = es;
    }

    @RequestMapping(value = "/words")
    public Iterable getWords(@RequestParam(value = "query",required = true) String query){
        return null;
    }
}
