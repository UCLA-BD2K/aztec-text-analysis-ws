/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ucla.cs.scai.aztec.ws;

import edu.ucla.cs.scai.aztec.AztecEntry;
import edu.ucla.cs.scai.aztec.dto.SearchResultPage;
import edu.ucla.cs.scai.aztec.similarity.Search;
import edu.ucla.cs.scai.aztec.similarity.SimilarityComputation;
import edu.ucla.cs.scai.aztec.similarity.WeightedEntry;
import it.zenitlab.jsonrpc.commons.JsonRpcError;
import it.zenitlab.jsonrpc.commons.JsonRpcException;
import it.zenitlab.jsonrpc.servlet.JsonRpcMethod;
import it.zenitlab.jsonrpc.servlet.JsonRpcService;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giuseppe M. Mazzeo <mazzeo@cs.ucla.edu>
 */
public class SearchService  extends JsonRpcService {
    
    @JsonRpcMethod(paramNames = {"query", "offset", "limit"}, description = "Returns the tools having the most similar description to the query.")
    public SearchResultPage getEntriesByQuery(String query, Integer offset, Integer limit) throws JsonRpcException {
        try {
            SearchResultPage res = new Search().searchQueryWithOnlyKeywordsTFIDF(query, offset, limit);
            return res;
        } catch (Exception ex) {
            Logger.getLogger(SimilarityService.class.getName()).log(Level.SEVERE, null, ex);
            throw new JsonRpcException(JsonRpcError.INTERNAL_ERROR, ex.getMessage(), "Internal error");
        }
    }    
    
}
