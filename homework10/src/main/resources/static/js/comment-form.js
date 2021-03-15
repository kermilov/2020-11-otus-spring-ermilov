function fillForm(bookCommentId, bookId, action) {

    if (bookCommentId > 0) {
        $.get('/book/'+bookId+'/comment/'+bookCommentId).done(function (bookComment) {
            document.getElementById('id-input').value = bookComment.id;
            document.getElementById('bookId-input').value = bookComment.bookId;
            document.getElementById('book-input').value = bookComment.book;
            document.getElementById('comment-input').value = bookComment.comment;
        });
    }
    else {
        $.get('/book/'+bookId).done(function (book) {
            document.getElementById('bookId-input').value = book.id;
            document.getElementById('book-input').value = book.name;
        });

    }

    if (action == 3) {
        document.getElementById('id-input').disabled = true;
        document.getElementById('bookId-input').disabled = true;
        document.getElementById('book-input').disabled = true;
        document.getElementById('comment-input').disabled = true;
        document.getElementById('submit-button').innerText = 'Delete';
    }
    else {
        document.getElementById('submit-button').innerText = 'Save';
    }

}
function doAction(type) {

    var bookComment = {
        'id': document.getElementById('id-input').value,
        'bookId': document.getElementById('bookId-input').value,
        'book': document.getElementById('book-input').value,
        'comment': document.getElementById('comment-input').value
    }

    const bookPath = '/book/' + document.getElementById('bookId-input').value;

    if (type == 1 || type == 2)
        $.ajax({
            url: bookPath
                +'/comment',
            type: 'POST',
            data: JSON.stringify(bookComment),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            async: true
        });

    if (type == 3)
        $.ajax({
            url: bookPath
                +'/comment/'+document.getElementById('id-input').value,
            type: 'DELETE',
            async: true
        });

    window.location.href = '/comment?id='+document.getElementById('bookId-input').value;

}