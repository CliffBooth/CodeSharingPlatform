<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Code</title>
    <style>
        #code_snippet {
            background: dimgray;
            color: beige;
            border: 2px solid black;
            min-height: 100px;
            min-width: 50%;
            max-width: 100%;
            width: fit-content;
        }

        #load_date {
            color: green;
        }
    </style>
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
<span id="load_date">
        ${(codeObj.date)!}
</span>
<br>
<#if codeObj.hasTime>
    <span id="time_restriction">
        <b>time left:</b> ${codeObj.time}
    </span>
<br>
</#if>
<#if codeObj.hasViews>
    <span id="views_restriction">
            <b>views left:</b> ${codeObj.views}
    </span>
<br>
</#if>
<pre id="code_snippet">
    <code>
        ${codeObj.code}
    </code>
</pre>
</body>
</html>