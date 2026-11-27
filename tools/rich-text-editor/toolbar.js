const marks = {
  Bold: 'bold', Italic: 'italic', Underline: 'underline',
  Strikethrough: 'strike', Highlight: 'highlight',
}
const headings = { Heading1: 1, Heading2: 2, Heading3: 3 }
const lists = { BulletList: 'bulletList', NumberList: 'orderedList' }
const alignments = { AlignLeft: 'left', AlignCenter: 'center', AlignRight: 'right', Justify: 'justify' }

function blockAttributes(editor) {
  return editor.getAttributes(editor.isActive('heading') ? 'heading' : 'paragraph')
}

export function toolbarState(editor) {
  const selected = []
  const block = blockAttributes(editor)
  for (const [action, mark] of Object.entries(marks)) {
    if (editor.isActive(mark)) selected.push(action)
  }
  for (const [action, level] of Object.entries(headings)) {
    if (editor.isActive('heading', { level })) selected.push(action)
  }
  for (const [action, type] of Object.entries(lists)) {
    if (editor.isActive(type)) selected.push(action)
  }
  for (const [action, alignment] of Object.entries(alignments)) {
    if ((block.textAlign || 'left') === alignment) selected.push(action)
  }
  if (block.indent) selected.push('Indent')
  if (editor.isActive('link')) selected.push('Link')
  return {
    selected, canUndo: editor.can().undo(), canRedo: editor.can().redo(),
    focused: editor.isFocused, url: editor.getAttributes('link').href || '',
  }
}

export function runAction(editor, action) {
  const chain = editor.chain().focus()
  if (marks[action]) return chain.toggleMark(marks[action]).run()
  if (headings[action]) return chain.toggleHeading({ level: headings[action] }).run()
  if (alignments[action]) return chain.setTextAlign(alignments[action]).run()
  switch (action) {
    case 'Undo': return chain.undo().run()
    case 'Redo': return chain.redo().run()
    case 'BulletList': return chain.toggleBulletList().run()
    case 'NumberList': return chain.toggleOrderedList().run()
    case 'Indent': {
      const indent = blockAttributes(editor).indent ? null : '24px'
      return chain.updateAttributes('paragraph', { indent }).updateAttributes('heading', { indent }).run()
    }
  }
}

export function finishLink(editor, selection, value) {
  const { anchor, head } = selection
  const chain = editor.chain().setTextSelection({ from: anchor, to: head }).focus()
  if (value == null) return chain.run()
  const href = /^[a-z][a-z\d+.-]*:/i.test(value) ? value : 'https://' + value
  try {
    if (!['http:', 'https:'].includes(new URL(href).protocol)) return chain.run()
  } catch {
    return chain.run()
  }
  if (anchor !== head) return chain.setLink({ href }).run()
  if (editor.isActive('link')) {
    return chain.extendMarkRange('link').setLink({ href }).setTextSelection(anchor).run()
  }
  return chain.insertContent({ type: 'text', text: value, marks: [{ type: 'link', attrs: { href } }] }).run()
}
