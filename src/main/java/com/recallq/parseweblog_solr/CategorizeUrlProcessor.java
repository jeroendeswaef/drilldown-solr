package com.recallq.parseweblog_solr;

import java.io.IOException;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.update.AddUpdateCommand;
import org.apache.solr.update.processor.UpdateRequestProcessor;

/**
 *
 * @author Jeroen De Swaef <j@recallq.com>
 */
public class CategorizeUrlProcessor extends UpdateRequestProcessor {

    public CategorizeUrlProcessor(UpdateRequestProcessor next) {
        super(next);
    }

    @Override
    public void processAdd(AddUpdateCommand cmd) throws IOException {
        SolrInputDocument doc = cmd.getSolrInputDocument();

        Object v = doc.getFieldValue("request-url");
        if (v != null) {
            String url = (String) v;
            if (url.contains(".js")) {
                doc.addField("request-cat", "javascript");
            } else if (url.contains(".css")) {
                doc.addField("request-cat", "css");
            } else if (url.contains(".php")) {
                doc.addField("request-cat", "php");
            } else {
                doc.addField("request-cat", "other");
            }
        }

        // pass it up the chain
        super.processAdd(cmd);
    }
}
