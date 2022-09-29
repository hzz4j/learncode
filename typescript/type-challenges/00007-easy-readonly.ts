// ============= Test Cases =============
import type { Equal, Expect } from "./test-utils";

type cases = [Expect<Equal<MyReadonly<Todo1>, Readonly<Todo1>>>];

interface Todo1 {
  title: string;
  description: string;
  completed: boolean;
  meta: {
    author: string;
  };
}

// ============= Your Code Here =============
//We need to make all the properties in the object read-only.
//Therefore, we need to iterate over all the properties and add a modifier to them
type MyReadonly<T> = {
  readonly [key in keyof T]: T[key];
};
