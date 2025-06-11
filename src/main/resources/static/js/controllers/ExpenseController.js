angular.module('expenseApp', [])
.controller('ExpenseController', function($scope, $http, $window) {

    $scope.showAddForm = false;
    $scope.formData = {};
    $scope.categories = ['Food','Transport','Entertainment','Clothes','Internet','House','Other'];
    $scope.formData.category = null;
    $scope.isEdit=false;
    $scope.curExpense=null;

    $http({method: 'GET', url: 'http://localhost:8080/expenses'})
        .then(function success(response) {
            $scope.expenses = response.data;
        });

    $scope.addExpense=function(){
        $scope.isEdit=false;
        $scope.formData = {};
        $scope.showEditForm = true;
    }
    $scope.editExpense=function(expense){
            $scope.isEdit=true;
            $scope.curExpense=angular.copy(expense);
            $scope.formData=angular.copy(expense);
            $scope.formData.date=new Date($scope.formData.date);
            $scope.showEditForm = true;
        }
    function formatDate(date) {
      var year = date.getFullYear();
      var month = (date.getMonth() + 1).toString().padStart(2, '0');
      var day = date.getDate().toString().padStart(2, '0');
      return year + '-' + month + '-' + day;
    }
    $scope.checkForm = function() {
        if($scope.isEdit){
            let data="?"
            if(!angular.equals($scope.curExpense.amount,$scope.formData.amount)){data+="amount="+$scope.formData.amount+'&'}
            if(!angular.equals($scope.curExpense.date,$scope.formData.date)){data+="date="+formatDate($scope.formData.date)+'&'}
            if(!angular.equals($scope.curExpense.category,$scope.formData.category)){data+="category="+$scope.formData.category+'&'}
            if(!angular.equals($scope.curExpense.description,$scope.formData.description)){data+="description="+$scope.formData.description}
            $http.put('http://localhost:8080/expenses/'+$scope.formData.id+data)
                            .then(function(response) {
                            console.log('Успешно обновлено', response.data);
                            $scope.showEditForm = false;
                            $window.location.reload();
                            }, function(error) {
                            console.error('Ошибка при обновлении', error);
                            });
            }
        else{
            $http.post('http://localhost:8080/expenses', $scope.formData)
                .then(function(response) {
                console.log('Успешно отправлено', response.data);
                $scope.showEditForm = false;
                $window.location.reload();
                }, function(error) {
                console.error('Ошибка при отправке', error);
                });
        }
    };
    $scope.deleteExpense = function(id){
        $http.delete('http://localhost:8080/expenses/'+id)
        .then(function(response) {
                console.log('Удаление успешно', response.data);
                $window.location.reload();
              })
              .catch(function(error) {
                console.error('Ошибка при удалении', error);
              });
    }



    $scope.cancelEdit = function() {
        $scope.showEditForm = false;
        console.log($scope.showEditForm);
    };
});