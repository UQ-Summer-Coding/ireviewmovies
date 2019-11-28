$(document).ready(function() {
    feather.replace();
});


function openMovieReviewsDetailsModal(movieReviewId) {
    $.ajax({
        url: "/user/my-reviews/" + movieReviewId,
        success: function (data) {
            $('#movieReviewDetailsModalHolder').html(data);
            $('#movieReviewDetailsModal').modal('show');
        }
    })
}