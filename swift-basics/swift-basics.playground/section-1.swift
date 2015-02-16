import UIKit


//==Basic Variables==
let myString = "This is a constant string"
let myInt = 3
let myDouble = 3.1459
let planetList = ["Mercury", "Venus", "Earth", "Mars"]
let myDictionary = ["cat": "Mammal", "ostrich": "Bird"]; //value type is String? (optional)
let emptyList = []
let emptyDictionarhy = [:]

var myOptionalString:String? = "hello" //{Some 'hello'}
var letterCountIndex = [String: Int]() //or equivalently Dictionary<String, Int>(), creates an empty map
letterCountIndex["apple"] = 5
letterCountIndex["orange"] = 6

println("My constant string: " + myString)
println("My constant int: " + String(myInt)); //no implicit casts
println("Alternative way to include values in strings \(myInt + 1)")

println("My optional string: " + myOptionalString!)
myOptionalString = "my optional string #2"
println("My optional string: " + myOptionalString!)


//==Control Structures==
if (myInt < 10){ //ifs must always evaluate to Booleans, and not the int0
    println("\(myInt) fits in my hand")
} else {
    println("\(myInt) doesn't fit in my hand")

}

//If the optional value is nil, the conditional is false. Otherwise, the optional value is unwrapped and assigned to the constant after let
if let option = myOptionalString {
    println("My optional string is set")
}

println("Cat is a " + myDictionary["cat"]!); //! needed to get optional out

for planet in planetList {
    println(planet) //will print each plaent
}

let airportToCity = ["YYZ":"Toronto", "SFO": "San Francisco", "ICN": "Seoul"]
for (airportCode, city) in airportToCity { //special sytax for iterating through dictioinaries
    println("\(airportCode) -> \(city)")
}

for i in 1...3 {  //could use 1..<3 to exclude 3
    println("\(i)th multiple of 7 is \(i*7)")
}

//==Functions
func greet(name: String) -> String{
    return ("hello \(name)!")
}
println(greet("Bob"))

//--tuple (a tuple can be named like sum here, otherwise they can be referered as 1, 2, etc
func findMinMax(scores: [Int]) -> (Int, Int, sum:Int) {
    var min = scores[0]
    var max = scores[0]
    var total = 0;
    for i in scores {
        if i>max {max=i}
        else if i<min {min=i}
        total = total + i
    }
    return (min, max, total)
}
println(findMinMax([9, 0, 2, 1, -7, 21]))
println(findMinMax([9, 0, 2]).sum)

//--vararg--
func sum(varargs: Int...) -> Int {
    var result = 0;
    for i in varargs {
        result = result + i
    }
    return result
}
sum(10, 20, 30)


//--higher order function
func filterList(xs: [Int], filterFunc:Int -> Bool) -> [Int] {
    var result:[Int] = []
    for i in xs {
        if (filterFunc(i)){
            result.append(i)
        }
    }
    return result
}

func lessThanTen(value: Int) -> Bool {
    return value < 10;
}

println(filterList([9, 11, 3, 6], lessThanTen)) //output: 9, 3, 6

//--closure (the syntax seems a bit weird, need a brace before the arg list and need to use 'in' instead of brace
filterList([1, 2, 3, 4, 5],
    {
        (i: Int) -> Bool in
            if (i % 2 == 0) {
                return true
            } else {
                return false
            }
    })