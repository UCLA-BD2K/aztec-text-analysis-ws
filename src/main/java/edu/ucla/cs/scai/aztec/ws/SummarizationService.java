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
import edu.ucla.cs.scai.aztec.summarization.KeywordsRank;
import edu.ucla.cs.scai.aztec.summarization.TextRank;
import it.zenitlab.jsonrpc.commons.JsonRpcError;
import it.zenitlab.jsonrpc.commons.JsonRpcException;
import it.zenitlab.jsonrpc.servlet.JsonRpcMethod;
import it.zenitlab.jsonrpc.servlet.JsonRpcService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giuseppe M. Mazzeo <mazzeo@cs.ucla.edu>
 */
public class SummarizationService extends JsonRpcService {

    @JsonRpcMethod(paramNames = {"text", "k"}, description = "Returns a text summarizing the input text. If k is specified, k sentences are returned, else k is chosen by keeping the sentences whose rank is at least 0.9 time the maximum rank.")
    public String summarize(String text, Integer k) throws JsonRpcException {
        try {
            TextRank tr=new TextRank(text);
            List<String> ts=tr.topSentences(k);
            StringBuilder sb=new StringBuilder();
            Iterator<String> it=ts.iterator();
            sb.append(it.next());
            while (it.hasNext()) {
                sb.append(" ").append(it.next());
            }
            return sb.toString();
        } catch (Exception ex) {
            Logger.getLogger(SummarizationService.class.getName()).log(Level.SEVERE, null, ex);
            throw new JsonRpcException(JsonRpcError.INTERNAL_ERROR, ex.getMessage(), "Internal error");
        }
    }
    
    @JsonRpcMethod(paramNames = {"text", "k"}, description = "Returns a list of the most relevant words in the text.")
    public List<String> keywords(String text, Integer k) throws JsonRpcException {
        try {
            KeywordsRank kr=new KeywordsRank(text, 10);
            List<String> ts=kr.topKeywords(k);
            return ts;
        } catch (Exception ex) {
            Logger.getLogger(SummarizationService.class.getName()).log(Level.SEVERE, null, ex);
            throw new JsonRpcException(JsonRpcError.INTERNAL_ERROR, ex.getMessage(), "Internal error");
        }
    }    
    
}
