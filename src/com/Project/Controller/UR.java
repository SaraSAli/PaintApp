package com.Project.Controller;

public class UR {
    public void undo(Canvas canvas) {
        if (canvas.UN.empty()) {
            System.out.println("You didn't draw anything!");
        } else {
            canvas.RE.push(canvas.UN.pop());
            canvas.shapes.remove(canvas.shapes.size() - 1);
            for (int i = canvas.shapes.size() - 1; i >= 0; i--) {
                canvas.shapes.get(i).draw(canvas.getGraphics());
            }
            canvas.flag = 0;
        }
    }

    public void redo(Canvas canvas) {
        if (canvas.RE.empty()) {
            System.out.println("You didn't draw anything yet!");
        } else {
            canvas.UN.push(canvas.RE.peek());
            canvas.shapes.add(canvas.RE.pop());

            for (int i = canvas.shapes.size() - 1; i >= 0; i--) {
                canvas.shapes.get(i).draw(canvas.getGraphics());
            }
            canvas.flag = 0;
        }

    }
}
