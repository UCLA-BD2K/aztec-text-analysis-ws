/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ucla.cs.scai.aztec.ws;

import edu.ucla.cs.scai.aztec.AztecEntry;
import edu.ucla.cs.scai.aztec.grants.ExtendedToken;
import edu.ucla.cs.scai.aztec.grants.GrantExtractor;
import edu.ucla.cs.scai.aztec.grants.MockGrantSentenceClassifier;
import edu.ucla.cs.scai.aztec.grants.Tokenizer;
import edu.ucla.cs.scai.aztec.similarity.DuplicateFinder;
import it.zenitlab.jsonrpc.commons.JsonRpcError;
import it.zenitlab.jsonrpc.commons.JsonRpcException;
import it.zenitlab.jsonrpc.servlet.JsonRpcMethod;
import it.zenitlab.jsonrpc.servlet.JsonRpcService;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giuseppe M. Mazzeo <mazzeo@cs.ucla.edu>
 */
public class GrantService extends JsonRpcService {

    @JsonRpcMethod(paramNames = {"text"}, description = "Returns the grant numbers found in the text.")
    public ArrayList<String> getGrants(String text) throws JsonRpcException {
        try {
            Tokenizer tokenizer = new Tokenizer();
            LinkedList<LinkedList<ExtendedToken>> tokenLists = tokenizer.tokenize(text);
            GrantExtractor ge = new GrantExtractor(new MockGrantSentenceClassifier());
            ArrayList<String> res = new ArrayList<String>();
            for (LinkedList<ExtendedToken> tokens : tokenLists) {
                for (String g : ge.extractGrants(tokens)) {
                    res.add(g);
                }
            }
            return res;
        } catch (Exception ex) {
            Logger.getLogger(GrantService.class.getName()).log(Level.SEVERE, null, ex);
            throw new JsonRpcException(JsonRpcError.INTERNAL_ERROR, ex.getMessage(), "Internal error");
        }
    }

    @JsonRpcMethod(paramNames = {"text"}, description = "Returns the grant agencies found in the text.")
    public ArrayList<String> getAgencies(String text) throws JsonRpcException {
        try {
            Tokenizer tokenizer = new Tokenizer();
            LinkedList<LinkedList<ExtendedToken>> tokenLists = tokenizer.tokenize(text);
            GrantExtractor ge = new GrantExtractor(new MockGrantSentenceClassifier());
            ArrayList<String> res = new ArrayList<String>();
            for (LinkedList<ExtendedToken> tokens : tokenLists) {
                for (String g : ge.extractAgency(tokens)) {
                    res.add(g);
                }
            }
            return res;
        } catch (Exception ex) {
            Logger.getLogger(GrantService.class.getName()).log(Level.SEVERE, null, ex);
            throw new JsonRpcException(JsonRpcError.INTERNAL_ERROR, ex.getMessage(), "Internal error");
        }
    }    
}
