package main

import "unicode/utf8"

func main() {
	// 反引号
	println(`First Line
			Another line.`)

	println(len("静默"))                       // 6
	println(utf8.RuneCountInString("静默"))    // 2
	println(utf8.RuneCountInString("Hi 静默")) // 5
}
