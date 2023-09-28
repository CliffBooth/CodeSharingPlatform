<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create</title>
    <!--    <link rel="stylesheet" href="style.css">-->
    <style>
        #code_snippet {
            background: dimgray;
            color: beige;
            border: 2px solid yellow;
            width: 50%;
            height: 200px;
        }
    </style>
</head>
<body>
<textarea id="code_snippet">//write your code here</textarea>
<br>
<button id="send_snippet" type="submit" onclick="send()">Submit</button>
<br>
<label>enter number of available time: <input type="number" id="time" value="0"></label>
<br>
<label>enter number of available views: <input type="number" id="views" value="0"></label>
<br>
<a href="/code/latest">latest</a>
</body>
<script>
    function send() {
        let object = {
            "code": document.getElementById("code_snippet").value,
            "time": document.getElementById("time").value,
            "views": document.getElementById("views").value
        };

        let json = JSON.stringify(object);

        let xhr = new XMLHttpRequest();
        xhr.open("POST", '/api/code/new', false)
        xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
        xhr.send(json);

        if (xhr.status == 200) {
            alert("Success!");
        }
    }

    document.getElementById('code_snippet').addEventListener('keydown', function(e) {
        if (e.key == 'Tab') {
            e.preventDefault();
            const start = this.selectionStart;
            const end = this.selectionEnd;
            console.log("start = " + start);
            console.log("end = " + end);
            console.log("value = " + this.value);
            this.text
            this.value = this.value.substring(0, start) + "\t" + this.value.substring(end);
            this.selectionStart =
                this.selectionEnd = start + 1;
        }
    });
</script>
</html>