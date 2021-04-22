function fillForm(bookId, action) {

    if (bookId != '') {
        $.get('/book/'+bookId).done(function (book) {
            document.getElementById('id-input').value = book.id;
            document.getElementById('name-input').value = book.name;
            document.getElementById('author-input').value = book.author;
            document.getElementById('genres-input').value = book.genres;
        });
    }

    if (action == 3) {
        document.getElementById('id-input').disabled = true;
        document.getElementById('name-input').disabled = true;
        document.getElementById('author-input').disabled = true;
        document.getElementById('genres-input').disabled = true;
        document.getElementById('submit-button').innerText = 'Delete';
    }
    else {
        document.getElementById('submit-button').innerText = 'Save';
    }

}
function doAction(type) {

    var book = {
        'id': document.getElementById('id-input').value,
        'name': document.getElementById('name-input').value,
        'author': document.getElementById('author-input').value,
        'genres': document.getElementById('genres-input').value
    }

    if (type == 1 || type == 2)
        $.ajax({
            url: '/book',
            type: 'POST',
            data: JSON.stringify(book),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            async: false
        });

    if (type == 3)
        $.ajax({
            url: '/book/'+document.getElementById('id-input').value,
            type: 'DELETE',
            async: false
        });

    window.location.href = '/';

}