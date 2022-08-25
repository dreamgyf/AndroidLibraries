# ShapeLayout

一种可以使用属性替代shape背景的布局，从此告别写shape xml

使用这个布局可以使用属性替代shape背景，避免重复创建大量相似但又细微不同的shape xml

```groovy
implementation 'com.dreamgyf.android.ui.widget:ShapeLayout:1.1'
```

![Demo](./ShapeLayout.png)

### 属性

属性名和shape属性名类似，加上了前缀

* `cornerRadius` --- 对应`corner`标签下的`radius`属性，圆角

* `cornerTopLeftRadius` --- 对应`corner`标签下的`topLeftRadius`属性，左上圆角

* `cornerTopRightRadius` --- 对应`corner`标签下的`topRightRadius`属性，右上圆角

* `cornerBottomRightRadius` --- 对应`corner`标签下的`bottomRightRadius`属性，右下圆角

* `cornerBottomLeftRadius` --- 对应`corner`标签下的`bottomLeftRadius`属性，左下圆角

---

* `gradientStartColor` --- 对应`gradient`标签下的`startColor`属性，渐变起始颜色

* `gradientCenterColor` --- 对应`gradient`标签下的`centerColor`属性，渐变中间颜色（可选）

* `gradientEndColor` --- 对应`gradient`标签下的`endColor`属性，渐变结束颜色

* `gradientAngle` --- 对应`gradient`标签下的`angle`属性，渐变角度，默认为270度，即从上向下

* `gradientType` --- 对应`gradient`标签下的`type`属性，渐变类型，分别为线性、圆形和扫射，默认为线性

* `gradientCenterX` --- 对应`gradient`标签下的`centerX`属性，相对X的渐变位置

* `gradientCenterY` --- 对应`gradient`标签下的`centerY`属性，相对Y的渐变位置

* `gradientRadius` --- 对应`gradient`标签下的`gradientRadius`属性，渐变颜色的半径

    **注：`gradientType`为 `radial`（圆形）的情况下，必须要设置`gradientRadius`，否则会报错**

---

* `solid` --- 对应`solid`标签下的`color`属性，填充颜色

---

* `strokeWidth` --- 对应`stroke`标签下的`width`属性，边框粗细

* `strokeColor` --- 对应`stroke`标签下的`color`属性，边框颜色

* `strokeDashWidth` --- 对应`stroke`标签下的`dashWidth`属性，虚线边框中，每条线段的长度

* `strokeDashGap` --- 对应`stroke`标签下的`dashGap`属性，虚线边框中，每条线段之间间隔的长度
