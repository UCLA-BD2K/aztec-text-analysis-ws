<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search demo</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
        <script type="text/javascript">
            var service_search_url = "http://localhost:8080/aztec-ws/search";

            function search() {
                var query = $("#query").val();
                if (!query) {
                    return;
                }
                query = query.trim();
                if (!query) {
                    return;
                }
                var offset = $("#offset").val().trim();
                if (offset==="" || isNaN(offset)) {
                    offset = 0;
                }
                var limit = $("#limit").val().trim();
                if (limit==="" || isNaN(limit)) {
                    limit = 1000000;
                }
                var id = Math.random().toString();
                var request = {"jsonrpc": "2.0", "id": id, "method": "getEntriesByQuery", "params": [query, offset, limit]};
                $.ajax({
                    type: "POST",
                    url: service_search_url,
                    data: JSON.stringify(request),
                    success: function (data) {
                        var html="<br /><br />Total results: "+data.result.totalResults;
                        for (var i=0; i<data.result.entries.length; i++) {
                            var e=data.result.entries[i];
                            html+="<br /><br /><br /><b>"+e.name+"</b> <i>"+e.description+"</i>";
                        }
                        $("#results").html(html);
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        //write your code here
                    }
                });
            }
        </script>
    </head>
    <body>
        <h1>Search demo</h1>
        Query <input id="query" />
        Offset <input id="offset" />
        Limit <input id="limit" />
        <input type="button" value="Search" onclick="javascript:search()" />
        <br />
        <div id="results"></div>
    </body>
</html>
