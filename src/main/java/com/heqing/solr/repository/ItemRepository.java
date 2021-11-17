package com.heqing.solr.repository;

import com.heqing.solr.model.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author heqing
 */
@Repository
public interface ItemRepository extends SolrCrudRepository<Item,String> {

    List<Item> findByName(String name);

    //    @Highlight(prefix = "<strong>",postfix = "</strong>")
//    HighlightPage<Item> findByKeywordsContaining(String keywords, Pageable page) ;

    @Highlight(prefix = "<strong>",postfix = "</strong>")
    HighlightPage<Item> findByNameContaining(String keywords, Pageable page) ;
}
