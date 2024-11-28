## Update: Nov 28, 2024

### KunWuJIe is a custom development framework based on the Bagel game engine from the University of Melbourne. The following documentation is for an older version and is provided for reference only, as many code implementations have already changed.

### One application of KunWuJie is the University of Melbourne's assignment, ShadowTaxi, which serves as a classic example of the KunWuJie framework.

## Update: Oct 11, 2024

I tried my best. I spent a lot of time on the engine package, and many of the concepts come from the game engine I created in Project 1. I iterated on it and introduced new abstractions, but I ran out of time. I'm not very satisfied with the specific implementation of the passenger and taxi related classes. The part I'm most proud of is the engine package.

## Update: Sep 04, 2024

# Shadow Taxi Game and its Engine “Kun WuJie”

I am a student with dreams, at least, that's what I believe (unfortunately, I was late). I developed my own game engine on top of the Bagel game engine, and I call it the "Kun WuJie" game engine.

Unfortunately, time was tight, and there were many other Due deadlines, which led to me not having enough time to choose better abstractions and optimize some details. This is the reason why there are magic numbers and some confusing logic. But there's no choice— the more fun I had, the lower the grades went, and I accept this reality.

## Introduction to the Kun WuJie Game Engine

The Kun WuJie game engine is a very simple 2D game engine that provides basic coordinate systems, drawing functions, collision detection, out-of-bound memory recycling, broadcasting (state sharing between game components), parent-child component systems, self-destruction, and other functions.

In Kun WuJie, every element has one and only one parent component, but it can have many child components, forming a tree structure. The game engine automatically controls all component input, state updates, and so on.

## Core Component Introduction

Next, I will introduce the core components of this game engine. All the following components are Classes, and you can check their implementation details in the `src` folder.

### Core Components

1. **GameElement**: The core component of the game engine, all game elements must inherit from it and implement its functions. The methods that must be implemented are `spreadIn`, `ctrlIn`, and `update`. These three methods from the `GameElement` class are the implementations of the aforementioned broadcasting, control input, and state updates. `GameElement` inherits from the `Loc` coordinate base class.

2. **GameElementBuilder**: A dynamic object generation system customized for this game, which dynamically generates objects as the Taxi moves forward to solve memory resource issues.

3. **Loc**: A coordinate base class. Most classes inherit from this base class, which provides functions such as movement boundary setting/detection and movement.

4. **FormatedImg** and **FormatedText**: These are two basic resources in the game, both inheriting from the `Loc` base class, providing the ability to draw images and text.

5. **ObjLinkMap**: A tool that combines a hash table and a list, used for quick reading and writing of object-related information, and is the underlying storage structure of the parent-child system.

6. **Sprite**: The sprite class of the game engine, inheriting from `GameElement`. On top of `GameElement`, it adds the `Image` object and uses the inherited `Loc` tools to control the drawing of `Image` objects. All game sprites will inherit from this class.

7. **EasyMap**: A hash table that supports three types: string, integer, and double.

8. **Status**: A configuration sharing component of the game engine, inheriting from `EasyMap`. It is a singleton pattern that shares global state, and any component in the game can pass information through this component.


## Introduction to GameElement Abstract Logic

To understand the entire game system, we need to first understand the abstract logic of `GameElement`. I will use the game Taxi to explain the working principle of `GameElement`.

When we build a Taxi, it will inherit from the `Sprite` class. Here, we can set our own Taxi image and implement some basic control functions through `public abstract void ctrlIn(Input input);` such as movement. Since the `Sprite` class inherits from the `Loc` class, we can implement movement and other operations in the `Taxi` class through `this.move(x,y)`.

Next, we can't just leave the Taxi alone in the center of the canvas. Unfortunately, we can't even draw this Taxi because we haven't implemented its drawing method.

To draw it on the screen, we also need to implement the `public void update(Input input)` method, which is the standard in the Kun WuJie game engine. We need to place all drawing code in the `update` method. In the `update` method, use the `this.draw();` method to call the image drawing function.

Alright, our basic game component Taxi is now built. (For specific implementation, see `Taxi.java`)
Wait, is that all?

Of course not, this is just the beginning. A game component cannot exist alone in a game framework, we need a parent component to control and manage the entire game, and of course, we also need to control our passenger objects.

## GameElement as a Parent Component and GameElementBuilder Builder

At this point, we need to create a game canvas to be responsible for the background scrolling and the management of all elements. We need to create a new class that inherits from `GameElement`, and we'll call it `GamePage`. In this class, we need to manually introduce two image objects since our sprite class does not currently support multi-image operations. We can manually implement the logic for background scrolling. Next, it's time to put the taxi into our `GamePage`. But? How do we do that?

Now it's time for our component `GameElementBuilder` to come into play. `GameElementBuilder` is a game element builder implemented for the Taxi game world file, and it initializes the builder module with the following code:

```java
this.gameElementBuilder = new GameElementBuilder(this);
```

We pass ourselves into it, and in the subsequent code, we call `this.gameElementBuilder.buildInRange(Int);`. This builder will call `GameElement`'s `parent.addSubElement(obj)` to add the elements that need to be drawn to the child element list of the current page. At this moment, we have achieved dynamic game element refreshing and parent-child component control.

`GameElementBuilder` will add all game objects that need to be drawn (these game objects are subclasses of `GameElement`) to the child elements of the current `GameElement`. Afterward, we no longer need to manually manage these elements. They will automatically propagate updates through the framework, and we just need to let go and allow our "children" to move freely. (As long as we write the appropriate logic in the children's classes)

Of course, if we want to manually add an instantiated `GameElement`, we can directly call `this.addSubElement(obj)` in the parent component to manually add the component to our child components. **Note**: Each parent component can have multiple child components, but each child component can only have one parent component. If you don't believe it, you can try it out.

Alright! Now our Taxi can be drawn. (For specific implementation, see `GamePage.java`)

But wait?
How strange, why can't our taxi move?

## Core Operation of Framework Automatic Update

Don't forget to call the `super.update(input);` method. This is a very, very important point. Why does the `update()` of `GameElement` have an `input` parameter? Because we need to call the parent class's `update` to ensure the update of lower-level child components. `super.update(input);` is the core secret of hierarchical updates in the framework.

But what about the passengers? Where are they?


## Introduction to the Sprite Class

Similarly, we need to implement the Passenger class by inheriting from the `Sprite` base class. (For specific implementation, see `Passenger.java`)

Likewise, passengers will also be automatically added to the game Page through the `GameElementbuilder`.

Alright.

So far, we already have a taxi and passengers. But isn't something still missing? Why can the taxi and passengers overlap, and why don't the passengers get on the taxi?

## DisTrigger and the Trigger Family

Now we need to introduce another part of the entire game framework: the `DisTrigger` interface and the `TriggerController` module. These two parts are customized trigger interfaces. To achieve the plug-and-play feature, I set them as interfaces. The `TriggerController` has already been integrated inside `GameElement`.

**Note!** All elements that inherit from the `Loc` base class can serve as trigger conditions! But! All triggered classes need to implement specific trigger interfaces.

For example, two game sprites, a ball and a child. The ball inherits from the `Loc` base class, so it can serve as a trigger condition, and the ball class does not need any changes and will automatically participate in the trigger detection process. However, if the child wants to be triggered by the ball, the corresponding trigger interface needs to be implemented.

In this game, passengers will be triggered by the taxi's position to perform specific behaviors, such as getting on the car. This requires us to implement the `DisTrigger` interface in the Passenger class:

```java
public class Passenger extends Sprite implements DisTrigger
```

While the taxi does not need any changes.
This is an open module that allows us to customize various triggers, implementing and using them freely in the future.
We must implement the `public void disTrigger(Loc obj)` method. The system framework will automatically pass objects with `Loc` into this trigger. We need to detect the object and set different operation logic for different objects, which can be implemented as needed.

So far, our passengers now have a distance trigger, and they can get on and off the taxi. (For specific implementation, see `Passenger.java`)

But wait, what about performance? It's ridiculous— all the elements are randomly triggering nonstop. What is this?
No, no, don't worry, I won't let that happen.

Our trigger scope has only two modes:

1. All child components of a parent component trigger each other, but the parent itself does not participate in the trigger.
2. A parent component triggers with its child components one by one, but child components do not trigger each other.

If you want, you can of course run both of these modes at the same time.

So how do you set which components trigger and which don't?
Good question!
In the parent class, simply call `this.subElementsTriggerThemSelf();` or `this.selfTriggerWithSubElements()`. (For specific examples, see `GamePage.java`)

Alright, most of the core components have now been introduced.



## Spread Broadcasting System

The last important component is the **spread broadcasting system**. This is also a built-in feature of `GameElement`.
`spread` is a `protected` `EasyMap` object, which is a multi-type hash table that allows subclasses to view it.

Our passengers have priority and distance parameters, which determine how much money a trip can earn, but these are the passengers' own properties. However, at the end of each trip, we need to pass the money to the `GamePage` object to update the game's overall state. How can we do this?

At this point, there are many ways to solve this problem, such as callback functions, state lifting, etc. Since time was really running out, I implemented a method called broadcasting.

All necessary parameters will be set in the `spread` object of the parent component, for example:

```java
spread.set("currentFee", (Double) 0.0);
```

Then, the game framework will automatically pass this object to the child objects through:

```java
public abstract void spreadIn(EasyMap spread);
```

In this method, the child objects can access the information transmitted by the parent object, read or modify it, and all modifications will take effect within the entire parent-child scope. This means that the parent's `spread` will be passed to all its child components but will not be passed to the grandchildren components. If you want to pass it to the grandchildren, you need to continue passing it in the child component with some additional settings.

For example, we just need to set in the `GamePage`:

```java
spread.set("currentFee", (Double) 0.0);
```

And in the `Passenger`'s `spreadIn(EasyMap spread)` method, set the current estimated value:

```java
spread.set("currentFee", estimateCost);
```

In this way, `GamePage` can receive the corresponding parameters.

Alright, this is the core principle of the entire game framework.

## Conclusion

There are no comments in my project, and the reason is that I really didn’t have time to write them. So I wrote this `Readme` tutorial to help you understand and learn my architecture.

Finally, I want to say that even though I didn’t solve the magic numbers and some details and minor features, I still believe I am a student with ideals.

After all, I was young and reckless, unaware of the vastness of the world.
Writing to the end, I look back and suddenly realize:

> "you are not required to use inheritance in this project."

