$(document).ready(function() {

    $('#loginModal').on('shown.bs.modal', function() {
    $('#loginForm').bootstrapValidator('resetForm', true);
});
   
    $('#signupModal').on('shown.bs.modal', function() {
    $('#signupForm').bootstrapValidator('resetForm', true);
});

    $.fn.bootstrapValidator.validators.securePassword = {
        validate: function(validator, $field, options) {
            var value = $field.val();
            if (value === '') {
                return true;
            }

            // Check the password strength
            if (value.length < 8) {
                return {
                    valid: false,
                    message: 'The password must be more than 8 characters long'
                };
            }

            // The password doesn't contain any uppercase character
            if (value === value.toLowerCase()) {
                return {
                    valid: false,
                    message: 'The password must contain at least one upper case character'
                }
            }

            // The password doesn't contain any uppercase character
            if (value === value.toUpperCase()) {
                return {
                    valid: false,
                    message: 'The password must contain at least one lower case character'
                }
            }

            // The password doesn't contain any digit
            if (value.search(/[0-9]/) < 0) {
                return {
                    valid: false,
                    message: 'The password must contain at least one digit'
                }
            }

            return true;
        }
    };
     $('form').bootstrapValidator({
        
        message: 'This value is not valid',
        excluded: [':disabled'],
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                group: '.col-md-6',
                validators: {
                    notEmpty: {
                        message: 'The username is required'
                    }
                    /*remote: {
                        type: 'POST',
                        url: 'checkUnique.php',
                        message: 'The username is not available',
                        delay: 1000
                    }*/
                }
            },
            password: {
                group: '.col-md-6',
                validators: {
                    notEmpty: {
                        message: 'The password is required'
                    },
                    securePassword: {
                        message:'The password is required'
                    },
                    different: {
                        field: 'username',
                        message: 'The password can\'t be the same as username'   
                }
                } 
            },										
            repassword: {
                group: '.col-md-6',
                validators: {
                    notEmpty: {
                        message: 'The password is required'
                    },
                identical: {
                        field: 'password',
                        message: 'The password and its confirm are not the same'
                    }   
                }
            },
            firstname: {
                group: '.col-md-6',
                validators: {
                    notEmpty: {
                        message: 'The password is required'
                    }
                }
            },
            lastname: {
                group: '.col-md-6',
                validators: {
                    notEmpty: {
                        message: 'The password is required'
                    }
                }
            },
            email: {
                group: '.col-md-6',                                  
                validators: {
                    notEmpty: {
                        message: 'The password is required'
                    },
                    emailAddress: {
                        message: 'This value is not a valid email address'
                    }
                }
            }
        }
    });
});