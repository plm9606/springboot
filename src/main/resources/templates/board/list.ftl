<!DOCTYPE html>
<html lang="en" xmlns>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <title>Board From</title>
</head>
<body>
    <header>
        <h1> 게시글 목록</h1>
    </header>
    <div class="container">
        <div class="pull-right">
            <a href="/board/" class="btn"> 등록</a>
        </div>
        <br/>

        <div class="main-hide">
            <table class="table table-hover">


                <thead>
                <tr>
                    <th class="col">#</th>
                    <th class="col-md-2">서비스 분류</th>
                    <th class="col-md-5">제목</th>
                    <th class="col-md-2">작성 날짜</th>
                    <th class="col-md-2">수정 날짜</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th scope="row">1</th>
                    <td>Mark</td>
                    <td>Otto</td>
                    <td>@mdo</td>
                    <td>${boardList.toString()}</td>
                </tr>
                <#list boardList.content as board>
                    <tr>
                        <th scope="row">${board.idx}</th>
                        <td>${board.boardType.value}</td>
                        <td><a href="/board?idx=${board.idx}">${board.title}</a></td>
                        <td>${board.createdDate}</td>
                        <td>${board.updatedDate}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>