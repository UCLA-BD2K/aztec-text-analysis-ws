/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ucla.cs.scai.aztec.ws;

import edu.ucla.cs.scai.aztec.AztecEntry;
import edu.ucla.cs.scai.aztec.similarity.DuplicateFinder;
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
public class SimilarityService extends JsonRpcService {

    @JsonRpcMethod(paramNames = {"entryId", "k"}, description = "Returns the k most similar entries to a given entry.")
    public ArrayList<AztecEntry> getSimilarEntries(String entryId, Integer k) throws JsonRpcException {
        try {
            ArrayList<WeightedEntry> res1 = new SimilarityComputation().getMostSimilarEntries(entryId, k, true);
            ArrayList<AztecEntry> res = new ArrayList<AztecEntry>();
            for (int i = 0; i < Math.min(res1.size(), k); i++) {
                res.add(res1.get(i).entry);
            }
            return res;
        } catch (Exception ex) {
            Logger.getLogger(SimilarityService.class.getName()).log(Level.SEVERE, null, ex);
            throw new JsonRpcException(JsonRpcError.INTERNAL_ERROR, ex.getMessage(), "Internal error");
        }
    }
    
    @JsonRpcMethod(paramNames = {"entryId", "k"}, description = "Returns the k most similar entries to a given entry.")
    public ArrayList<AztecEntry> getSimilarEntriesWithSeparateTags(String entryId, Integer k) throws JsonRpcException {
        try {
            ArrayList<WeightedEntry> res1 = new SimilarityComputation().getMostSimilarEntriesWithSeparateTags(entryId, k);
            ArrayList<AztecEntry> res = new ArrayList<AztecEntry>();
            for (int i = 0; i < Math.min(res1.size(), k); i++) {
                res.add(res1.get(i).entry);
            }
            return res;
        } catch (Exception ex) {
            Logger.getLogger(SimilarityService.class.getName()).log(Level.SEVERE, null, ex);
            throw new JsonRpcException(JsonRpcError.INTERNAL_ERROR, ex.getMessage(), "Internal error");
        }
    }    
    
    @JsonRpcMethod(paramNames = {"entryId"}, description = "Returns the possible duplicates for a given entry.")
    public ArrayList<AztecEntry> getPossibleDuplicates(String entryId) throws JsonRpcException {
        try {
            ArrayList<AztecEntry> res = new DuplicateFinder().find(entryId);
            return res;
        } catch (Exception ex) {
            Logger.getLogger(SimilarityService.class.getName()).log(Level.SEVERE, null, ex);
            throw new JsonRpcException(JsonRpcError.INTERNAL_ERROR, ex.getMessage(), "Internal error");
        }
    }
}
