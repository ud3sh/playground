import UIKit


//==Basic Variables==
let myString = "This is a constant string"
let myInt = 3
let myDouble = 3.1459
let firstTenPrimes = [2, 3, 5, 7, 11, 13, 17, 19, 23, 29] //this is an array of Int, i.e type [Int]
let planetList = ["Mercury", "Venus", "Earth", "Mars"] //this is an array of String, i.e type [String]
let myDictionary = ["cat": "Mammal", "ostrich": "Bird"]; //value type is String? (optional), type Dictionary<String, String>
let emptyList = []
let emptyDictionary = [:]

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


//==Strings ==
var cafe = "cafe";
var cafe2 = "café";

println(countElements(cafe)) //note cafe.length shouldn't be used as is the number of 16-bit code units rather than the number of unicode chars
println(countElements(cafe))

if (cafe == cafe2){
    println("\(cafe) and \(cafe2) are the same")
} else {
    println("\(cafe) and \(cafe2) are not the same")
}

cafe2 += " is closed"
println(cafe2)

var cafe3 = cafe2; //strings are copied by value in swift, like Int, etc
println(cafe3)


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

//note ..., and ..< are range operators

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

firstTenPrimes.map({n in n * 2}) //double the first ten primes
firstTenPrimes.map({$0 * 2}) //same as above but the first param is represented by $0 (i.e annonamized)

//--nested functions--
//  note: local functions cannot refer to themselves (yikes!!!)
func fib(n:Int) -> Int {
    func fibIter (target: Int) -> Int {
        if (target == 0){
            return 0
        }
        var curr = 1;
        var prev = 0;
        for i in 1...target {
            var tmp = curr
            curr = prev + curr
            prev = tmp
        }
        return prev;
        
    }
    return fibIter(n);
}
fib(0)
fib(1)
fib(2)
fib(10)

//==Classes==

class Shape {
    let numberOfSides: Int
    init (sides: Int){ //constructor
        self.numberOfSides = sides; //self is used for this
    }
    func getNumberOfSides() -> Int {
        return self.numberOfSides;
    }
    func getDescription() -> String {
        return "A Shape with \(numberOfSides) sides";
    }
}

class Square: Shape { //extends Shape
    let length: Double
    init(sideLength: Double) {
        self.length = sideLength;
        super.init(sides: 4) //super's init must be called after this classes members are initialized (opposite of Java)
    }
    
    func getArea() -> Double {
        return length * length;
    }
    override func getDescription() -> String {
        return "Square of length \(length)"
    }
}
let sqr = Square(sideLength: 10)
println(sqr.getDescription())
println("A square has \(sqr.numberOfSides) sides")

class MutableEquilateralTriangle: Shape {
    var length: Double;
    
    var perimeter: Double { //variable with getter an setter
        get {
            return self.length * 3
        }
        set {
            self.length = newValue / 3.0 //by default newValue is the formalParam name
        }
    }
    init (sideLength: Double) {
        self.length = sideLength
        super.init(sides: 3)
    }
    
    func getArea() -> Double {
        return sqrt(3)/4 * Square(sideLength: self.length).getArea()
    }
    
    override func getDescription() -> String {
        return "Equilateral triangle of length \(length), having permiter \(perimeter) and area \(getArea())"
    }
}

var equilateralTriangle = MutableEquilateralTriangle(sideLength: 3.0);
println(equilateralTriangle.getDescription())
equilateralTriangle.perimeter = 7;
println(equilateralTriangle.getDescription())


//==enums
enum Suit {
    case SPADE, DIAMOND, HEART, CLUB
    func description() -> String {
        switch self {
        case SPADE: return "spades"
        case DIAMOND: return "diamonds"
        case HEART: return "hearts"
        case CLUB: return "clubs"
        }
    }
}

class Card {
    let number: Int
    let suit: Suit
    init(number:Int, suit: Suit){
        self.number = number
        self.suit = suit
    }
    func getDescription() -> String {
        switch self.number {
        case 1:
            return "Ace of " + suit.description()
        case 10:
            return "Jack of " + suit.description()
        case 11:
            return "Queen of " + suit.description()
        case 12:
            return "King of " + suit.description()
        default:
            return "\(String(self.number)) of \(self.suit.description())"
        }
    }
}

println(Card(number: 1, suit: Suit.DIAMOND).getDescription())

//--enums with parameters--
enum Response {
    case Success(String)
    case Error(String, String)
}

func handleResponse(response: Response) {
    switch (response){
    case let .Error(code, msg): println("Error! \(code) \(msg)")
    case let .Success(msg): println(msg)
    }
}
let failResponse = Response.Error("401", "Obtaining client list")
let okResponse = Response.Success("Obtained client list!")
handleResponse(failResponse)
handleResponse(okResponse)


//==generics==

//==protocols==

protocol EntityWithId {
    var id: Int {get}
}

protocol Clonable {
    typealias T //note: in protocols, generic types must be typealiased
    mutating func clone()-> T
}

class Person {
    let firstName: String
    let lastName: String
    var fullName: String {get {return self.firstName + " " + self.lastName}}
    init(firstName: String, lastName:String) {
        self.firstName = firstName
        self.lastName = lastName
    }
    
    
}

class ClonablePerson: Person, Clonable, EntityWithId {
    typealias T = ClonablePerson
    var id: Int
    init (id:Int, firstName:String, lastName:String){
        self.id = id
        super.init(firstName: firstName, lastName: lastName)
    }
    func clone() -> ClonablePerson {
        return ClonablePerson(id: self.id, firstName: self.firstName, lastName: self.lastName)
    }
}
var clonablePerson = ClonablePerson(id:10, firstName: "Alan", lastName: "Turing")
println(clonablePerson.fullName)
println(clonablePerson.clone().fullName)





