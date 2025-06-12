angular.module('expenseApp', [])
.controller('ExpenseController', function($scope, $http, $window) {

    $scope.showAddForm = false; //Отображение формы
    $scope.formData = {}; //Данные формы
    $scope.categories = ['Food','Transport','Entertainment','Clothes','Internet','House','Other']; //Категории для списка
    $scope.formData.category = null; //Текущая категория
    $scope.isEdit=false; //Режим формы (редактирование или добавление)
    $scope.curExpense=null; //Редактируемый объект
    $scope.filterType='По дате' //Тип фильтра
    $scope.filterTypes = ['По id','По дате']; //Список фильтров
    $scope.dateFilter={}; //Данные фильтра даты
    $scope.filter = {
      idFilter: null
    }; //Данные фильтра id
    $scope.curSum=0; //Сумма выборки

//    $http({method: 'GET', url: 'http://localhost:8080/expenses'})
//        .then(function success(response) {
//            $scope.expenses = response.data;
//        });}
    function applyFilter(){
        if($scope.filterType=='По дате'){
            let data="?";
            if($scope.dateFilter.date1!=null){
                data+="date1="+formatDate($scope.dateFilter.date1)+'&'
            }
            if($scope.dateFilter.date2!=null){
                            data+="date2="+formatDate($scope.dateFilter.date2)+'&'
                        }
            $http({method: 'GET', url: 'http://localhost:8080/expenses'+data})
                    .then(function success(response) {
                        $scope.expenses = response.data;
                    });
            $http({method: 'GET', url: 'http://localhost:8080/expenses/summary'+data})
                                .then(function success(response) {
                                    $scope.curSum = response.data;
                                });
        }
        else{
            if($scope.filter.idFilter){
            $http({method: 'GET', url: 'http://localhost:8080/expenses/'+$scope.filter.idFilter})
                                .then(function success(response) {
                                    $scope.expenses = [response.data];
                                });
            }
        }
    }
    $scope.callApplyFilter=function(){
        applyFilter();
    }
    applyFilter();

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
                            applyFilter()
                            }, function(error) {
                            console.error('Ошибка при обновлении', error);
                            });
            }
        else{
            $http.post('http://localhost:8080/expenses', $scope.formData)
                .then(function(response) {
                console.log('Успешно отправлено', response.data);
                $scope.showEditForm = false;
                applyFilter()
                }, function(error) {
                console.error('Ошибка при отправке', error);
                });
        }
    };
    $scope.deleteExpense = function(id){
        $http.delete('http://localhost:8080/expenses/'+id)
        .then(function(response) {
                console.log('Удаление успешно', response.data);
                applyFilter()
              })
              .catch(function(error) {
                console.error('Ошибка при удалении', error);
              });
    }



    $scope.cancelEdit = function() {
        $scope.showEditForm = false;
    };
});