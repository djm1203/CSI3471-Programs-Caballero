#include <iostream>
#include <fstream>

using namespace std;

class MyVector {
public:
    MyVector();
    ~MyVector();
    MyVector& push_back(double);
    double& operator[](int);
    int getSize();
protected:
    double *data;
    int size;
    int capacity;
    void grow();
};

MyVector::MyVector(){
    size = 0;
    capacity = 10;
    data = new double[capacity];
}

MyVector::~MyVector(){
    delete[] data;
}

MyVector& MyVector::push_back(double e){
    if(size >= capacity){
        this->grow();
    }
    data[size] = e;
    size++;
    return *this;
}

double&  MyVector::operator [](int ndx){
    return this->data[ndx];
}

int MyVector::getSize(){
    return this->size;
}

void MyVector::grow(){
    this->capacity *= 2;
    double *cpy = data;
    data = new double[capacity];
    for(int i = 0; i < size; i++){
        data[i] = cpy[i];
    }
    delete[] cpy;
}

void merge(MyVector& v, int left, int middle, int right){
    int n1 = middle - left + 1;
    int n2 = right - middle;

    MyVector leftV;
    MyVector rightV;

    for(int i = 0; i < n1; i++){
        leftV.push_back(v[left + i]);
    }
    for(int j = 0; j < n2; j++){
        rightV.push_back(v[middle + 1 + j]);
    }

    int i = 0, j = 0, k = left;
    while(i < n1 && j < n2){
        if(leftV[i] <= rightV[j]){
            v[k] = leftV[i];
            i++;
        }else{
            v[k] = rightV[j];
            j++;
        }
        k++;
    }
    while(i < n1){
        v[k] = leftV[i];
        i++;
        k++;
    }
    while(j < n2){
        v[k] = rightV[j];
        j++;
        k++;
    }
}

void mergeSort(MyVector& v, int left, int right){
    if(left < right){
        int middle = left + (right - left) / 2;

        mergeSort(v, left, middle);
        mergeSort(v, middle + 1, right);

        merge(v, left, middle, right);
    }
}

int binarySearch(MyVector& v,double val){
    int left = 0;
    int right = v.getSize() - 1;

    while(left <= right){
        int mid = left + (right - left) / 2;

        if(v[mid] == val){
            return mid;
        }else if(v[mid] < val){
            left = mid + 1;
        }else{
            right = mid - 1;
        }
    }

    return left;
}

void printAndSortList(MyVector& v){
    mergeSort(v, 0, v.getSize() - 1);

    cout << "Sorted List:" << endl;
    for(int i = 0; i < v.getSize(); i++){
        cout << v[i] << " ";
    }
    cout << endl;
}

int main(int argc, char** argv){
    MyVector numbers;
    double num;
    double sum = 0;

    const char* fileName = argv[1];
    double userVal = stod(argv[2]);
    ifstream inFile(fileName);

    try {
        if(!inFile) throw runtime_error(string(fileName) + "does not exist.");

        while (inFile >> num) {
            numbers.push_back(num);
            sum += num;
        }
        if(inFile.fail() && !inFile.eof()){
            throw runtime_error("ERROR: Non-number input.");
        }

        cout << "Result: " << sum << endl;
        printAndSortList(numbers);

        cout << "Inserting value at ndx: " << binarySearch(numbers, userVal) << endl;
        numbers.push_back(userVal);
        printAndSortList(numbers);

    }catch (const exception& e){
        cerr << "ERROR: " << e.what() << endl;
        return 1;
    }

    inFile.close();
    return 0;
}