function fillForm(bookId) {

    $.get('/book/'+bookId).done(function (book) {
        $('h1').append(book.name + ' Comments:')
    });

    $.get('book/'+bookId+'/comment').done(function (bookcomments) {
        bookcomments.forEach(function (bookcomment) {
            $('tbody').append(`
               <tr>
                   <td>${bookcomment.id}</td>
                   <td>${bookcomment.comment}</td>
                    <td>
                        <a href="/comment/edit?id=${bookcomment.id}">Edit</a>
                    </td>
                    <td>
                        <a href="/comment/delete?id=${bookcomment.id}">Delete</a>
                    </td>
               </tr>
           `)
        });
    })

}
function doAction(bookId) {

    window.location.href = '/comment/insert?id='+bookId;

}